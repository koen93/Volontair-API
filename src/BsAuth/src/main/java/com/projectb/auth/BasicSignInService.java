package com.projectb.auth;

import com.projectb.repositories.UserRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class BasicSignInService implements SignInService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailsManager userDetailsManager;

    public void signIn(@NonNull String username) {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(GrantedAuthority role : userDetails.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), authorities));
    }
}
