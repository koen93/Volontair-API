package com.projectb.auth;

import com.projectb.entities.User;

public interface PrincipalService {
    User getAuthenticatedUser();
}
