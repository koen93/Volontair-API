package com.projectb.auth;

import com.projectb.entities.User;
import com.projectb.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

@RequiredArgsConstructor
public class UserConnectionSignUp implements ConnectionSignUp {

    private final UserRepo userRepo;

    private final SignUpService signUpService;

    @Override
    public String execute(Connection<?> connection) {
        UserProfile profile = connection.fetchUserProfile();

        try {
            User user = new User();
            user.setUsername(profile.getEmail());
            user.setPassword("generate"); // TODO: Generate a password, or better yet. Or disable local login.
            user.setName(profile.getName());

            signUpService.signUp(user);

            return user.getUsername();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
