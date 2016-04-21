package com.projectb.auth;

import com.projectb.entities.Role;
import com.projectb.entities.User;
import com.projectb.repositories.UserRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class BasicSignInService implements SignInService {
    @Autowired
    private UserRepo userRepo;

    public void signIn(@NonNull String username) {
        User user = userRepo.findByUsername(username);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities));
    }
}
