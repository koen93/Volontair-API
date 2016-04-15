package com.projectb.auth;

import com.projectb.entities.Role;
import com.projectb.entities.User;
import com.projectb.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleSignInAdapter implements SignInAdapter {
    @Autowired
    private UserRepo userRepo;

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        User user = userRepo.findByUsername(userId);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, user.getPassword(), authorities));

        return null;
    }
}
