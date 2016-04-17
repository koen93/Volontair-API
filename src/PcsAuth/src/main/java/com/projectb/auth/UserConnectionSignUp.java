package com.projectb.auth;

import com.projectb.entities.Role;
import com.projectb.entities.User;
import com.projectb.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class UserConnectionSignUp implements ConnectionSignUp {
    private final UserRepo userRepo;

    @Override
    public String execute(Connection<?> connection) {
        UserProfile profile = connection.fetchUserProfile();

        try {
            User user = new User();
            user.setUsername(profile.getEmail());
            user.setEnabled(true);
            user.getRoles().add(new Role("ROLE_USER"));
            userRepo.saveAndFlush(user);

            return user.getUsername();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
