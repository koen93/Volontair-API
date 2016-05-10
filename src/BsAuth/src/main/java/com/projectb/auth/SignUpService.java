package com.projectb.auth;

import com.projectb.entities.Account;

public interface SignUpService {
    Account signUp(Account account);
    Account signUp(String username, String password);
}
