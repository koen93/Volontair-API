package com.projectb.auth;

import com.projectb.entities.User;
import com.projectb.entities.Role;
import com.projectb.repositories.RoleRepo;
import com.projectb.repositories.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;

@Service
@NoArgsConstructor
@AllArgsConstructor
public final class BasicUserDetailsManager implements UserDetailsManager {
    private static final Log logger = LogFactory.getLog(BasicUserDetailsManager.class.getClass());

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException(String.format("Username %s could not be found.", username));
        return new BasicUserDetails(user);
    }

    @Override
    public void createUser(UserDetails userDetails) {
        validateUserDetails(userDetails);

        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setEnabled(userDetails.isEnabled());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for(GrantedAuthority authority : authorities) {
            Role role = roleRepo.findByName(authority.getAuthority());
            if(role != null)
                user.getRoles().add(role);
            else
                logger.warn(String.format("Could not find role %s.", authority.getAuthority()));
        }

        signUpService.signUp(user);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        validateUserDetails(userDetails);

        User user = userRepo.findByUsername(userDetails.getUsername());
        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setEnabled(userDetails.isEnabled());

//        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//        user.getRoles().clear();
//        for(GrantedAuthority authority : authorities) {
//            user.getRoles().add(new Role(authority.getAuthority()));
//        }

        userRepo.save(user);
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepo.findByUsername(username);
        userRepo.delete(user);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext()
                .getAuthentication();

        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException(
                    "Can't change password as no Authentication object found in context "
                            + "for current user.");
        }

        String username = currentUser.getName();

        // If an authentication manager has been set, re-authenticate the user with the
        // supplied password.
        if (authenticationManager != null) {
            logger.debug("Reauthenticating user '" + username
                    + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, oldPassword));
        }
        else {
            logger.debug("No authentication manager set. Password won't be re-checked.");
        }

        logger.debug("Changing password for user '" + username + "'");

        User user = userRepo.findByUsername(username);
        user.setPassword(newPassword);

        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentUser, newPassword));
    }

    private Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
        UserDetails userDetails = loadUserByUsername(currentAuth.getName());
        User user = userRepo.findByUsername(userDetails.getUsername());

        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());

        return newAuthentication;
    }

    @Override
    public boolean userExists(String username) {
        User user = userRepo.findByUsername(username);

        // TODO: Throw exception when more than one user is found like JdbcUserDetailsManager?

        return user != null;
    }

    private void validateUserDetails(UserDetails userDetails) {
        Assert.hasText(userDetails.getUsername(), "Username may not be empty or null");
        validateAuthorities(userDetails.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");

        for(GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(), "getAuthority() methods return a non-empty string");
        }
    }
}
