package com.projectb.auth;

import com.projectb.entities.Account;
import com.projectb.entities.Role;
import com.projectb.repositories.RoleRepo;
import com.projectb.repositories.UserRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BasicSignUpService implements SignUpService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account signUp(@NonNull Account account) {
        if(account.getUsername() == null && account.getUsername().isEmpty())
            throw new IllegalArgumentException("Username may not be null or empty.");
        if(account.getPassword() == null & account.getPassword().isEmpty())
            throw new IllegalArgumentException("Password may not be null or empty.");

        account.setEnabled(true);

        Role role = roleRepo.findByName("ROLE_USER");
        if(role == null)
            throw new IllegalStateException("Could not find 'ROLE_USER' role.");
        account.getRoles().add(role);

        userRepo.save(account);

        return account;
    }

    public Account signUp(@NonNull String username, @NonNull String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        signUp(account);

        return account;
    }
}
