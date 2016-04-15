package com.projectb.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.connect.UserProfile;

@Getter
@Setter
public class SignupForm {
    private String username;
    private String password;

    public static SignupForm fromProviderUser(UserProfile userProfile) {
        SignupForm form = new SignupForm();

        form.setUsername(userProfile.getEmail());

        return form;
    }
}
