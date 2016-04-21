package com.projectb.endpoint;

import com.projectb.auth.PrincipalService;
import com.projectb.entities.User;
import com.projectb.exception.IdDoesNotMatchResourceException;
import com.projectb.exception.ResourceNotOwnedByPrincipalException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
public class UserServiceController {
    @Autowired
    private PrincipalService principalService;

    @Autowired
    private UserService userService;

    @Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public @ResponseBody ResponseEntity<?> update(@RequestBody User user, @PathVariable("id") long id) {
        if(user.getId() != id)
            throw new IdDoesNotMatchResourceException();
        User userToUpdate = userService.findOne(id);
        if(userToUpdate == null)
            throw new ResourceNotFoundException();
        User authenticatedUser = principalService.getAuthenticatedUser();
        if(!userToUpdate.getId().equals(authenticatedUser.getId()))
            throw new ResourceNotOwnedByPrincipalException();

        userService.merge(user);
        return ResponseEntity.ok(user);
    }
}
