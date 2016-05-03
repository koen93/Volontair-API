package com.projectb.endpoint;

import com.projectb.auth.PrincipalService;
import com.projectb.entities.User;
import com.projectb.exception.CouldNotProcessAvatarException;
import com.projectb.exception.IdDoesNotMatchResourceException;
import com.projectb.exception.ResourceNotOwnedByPrincipalException;
import com.projectb.repositories.UserRepo;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;

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
    public @ResponseBody ResponseEntity<User> me() {
        User authenticatedUser = principalService.getAuthenticatedUser();
        return ResponseEntity.ok(authenticatedUser);
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

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}/avatar.png")
    @ResponseBody
    public void avatar(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        File avatarFile = getAvatarPath(id);
        if(!avatarFile.exists())
            avatarFile = new File(getAvatarBasePath() + File.separator + "none.png");

        try(InputStream in = new BufferedInputStream(new FileInputStream(avatarFile))) {
            // TODO: Rewrite to return proper FileSystemResource using a proper HttpMessageConverter
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            IOUtils.copy(in, response.getOutputStream());
        }
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
}
