package com.projectb.auth;

import com.projectb.entities.User;

public interface SignUpService {
    User signUp(User user);
    User signUp(String username, String password);
}
