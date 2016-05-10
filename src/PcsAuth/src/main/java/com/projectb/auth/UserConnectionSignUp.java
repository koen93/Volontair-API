package com.projectb.auth;

import com.projectb.entities.Account;
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
            Account account = new Account();
            account.setUsername(profile.getEmail());
            account.setPassword("generate"); // TODO: Generate a password, or better yet. Or disable local login.
            account.setName(profile.getName());

            signUpService.signUp(account);

            return account.getUsername();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
