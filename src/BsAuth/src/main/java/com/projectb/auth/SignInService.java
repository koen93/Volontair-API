package com.projectb.auth;

public interface SignInService {
    void signIn(String username);

    void signInSocial(String username, String imageUrl);
}
