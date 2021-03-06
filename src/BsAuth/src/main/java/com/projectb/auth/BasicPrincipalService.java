package com.projectb.auth;

import com.projectb.entities.User;
import com.projectb.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class BasicPrincipalService implements PrincipalService {
    @Autowired
    private UserRepo userRepo;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepo.findByUsername(userDetails.getUsername()); // TODO: Retrieve from principal instead of querying to improve speed
        return user;
    }
}
