package com.projectb.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.connect.UserProfile;

@Getter
@Setter
public class SignUpForm {
    private String username;
    private String password;

    public static SignUpForm fromProviderUser(UserProfile userProfile) {
        SignUpForm form = new SignUpForm();

        form.setUsername(userProfile.getEmail());

        return form;
    }
}
