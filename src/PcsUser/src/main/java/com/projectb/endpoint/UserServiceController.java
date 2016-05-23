package com.projectb.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectb.auth.PrincipalService;
import com.projectb.entities.User;
import com.projectb.exception.CouldNotProcessAvatarException;
import com.projectb.exception.IdDoesNotMatchResourceException;
import com.projectb.exception.ResourceNotOwnedByPrincipalException;
import com.projectb.repositories.UserRepo;
import org.apache.commons.io.IOUtils;
import org.hibernate.type.ImageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.http.*;
import org.springframework.social.support.URIBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

@RepositoryRestController
public class UserServiceController {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class.getSimpleName());

    @Autowired
    private PrincipalService principalService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Environment environment;

    @RequestMapping(method = RequestMethod.GET, value = "/users/me")
    public @ResponseBody ResponseEntity<Resource<?>> me(PersistentEntityResourceAssembler assembler) {
        User authenticatedUser = principalService.getAuthenticatedUser();

        PersistentEntityResource resource = assembler.toFullResource(authenticatedUser);
        return new ResponseEntity<Resource<?>>(resource, null, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public @ResponseBody ResponseEntity<?> update(@RequestBody User user, @PathVariable("id") long id) {
        if(user.getId() != id)
            throw new IdDoesNotMatchResourceException();
        User userToUpdate = userRepo.findOne(id);
        if(userToUpdate == null)
            throw new ResourceNotFoundException();
        User authenticatedUser = principalService.getAuthenticatedUser();
        if(!userToUpdate.getId().equals(authenticatedUser.getId()))
            throw new ResourceNotOwnedByPrincipalException();

        userRepo.save(user);
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}/avatar")
    public @ResponseBody ResponseEntity<InputStreamResource> avatar(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        URL facebookImageUrl = getFacebookImageUrl(id);
        if(facebookImageUrl != null) {
            InputStreamResource inputStreamResource = new InputStreamResource(facebookImageUrl.openStream());

            return ResponseEntity
                    .ok()
                    .contentLength(inputStreamResource.contentLength())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(inputStreamResource);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users/{id}/avatar")
    public @ResponseBody ResponseEntity<?> uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable("id") long id) {
        if(!file.isEmpty()) {
            // Create path and directories
            File dir = getAvatarBasePath();

            // Create file on server
            File avatarFile = getAvatarPath(id);

            try(BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(avatarFile))) {
                BufferedImage image = ImageIO.read(file.getInputStream());
                BufferedImage resizedImage = fitImage(image);
                ImageIO.write(resizedImage, "png", avatarFile);

                // Update user
                logger.info("Uploaded avatar %s for %d to %s", file.getName(), id, avatarFile.getAbsoluteFile());
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                throw new CouldNotProcessAvatarException(e);
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private BufferedImage fitImage(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // scale image
        double scale;
        if (width >= height) {
            // horizontal or square image
            scale = Math.min(100, width) / (double) width;
        } else {
            // vertical image
            scale = Math.min(100, height) / (double) height;
        }

        BufferedImage newImage = new BufferedImage((int) (scale * width), (int) (scale * height), originalImage.getType());

        Graphics2D g = newImage.createGraphics();
        AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);
        g.drawImage(originalImage, transform, null);

        return newImage;
    }

    private File getAvatarBasePath() {
        String avatarPath = environment.getProperty("volontair.user.avatarPath");

        File dir = new File(avatarPath);
        if(!dir.exists()) {
            if(!dir.mkdirs())
                throw new CouldNotProcessAvatarException();
        }

        return dir;
    }

    private File getAvatarPath(long id) {
        return new File(getAvatarBasePath() + File.separator + id + ".png");
    }

    private URL getFacebookImageUrl(long id) {
        URL faceBookImageUrl = null;
        try {
            faceBookImageUrl = new URL(userRepo.getOne(id).getImageUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return faceBookImageUrl;
    }
}
