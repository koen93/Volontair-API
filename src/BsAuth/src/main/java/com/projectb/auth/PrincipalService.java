package com.projectb.auth;

import com.projectb.entities.Account;

public interface PrincipalService {
    Account getAuthenticatedUser();
}
