package com.projectb.auth;

import com.projectb.entities.Role;
import com.projectb.entities.User;
import com.projectb.repositories.RoleRepo;
import com.projectb.repositories.UserRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BasicSignUpService implements SignUpService {
    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BasicSignUpService(@NonNull UserRepo userRepo, @NonNull RoleRepo roleRepo, @NonNull PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(@NonNull User user) {
        if(user.getUsername() == null && user.getUsername().isEmpty())
            throw new IllegalArgumentException("Username may not be null or empty.");
        if(user.getPassword() == null & user.getPassword().isEmpty())
            throw new IllegalArgumentException("Password may not be null or empty.");

        user.setEnabled(true);

        Role role = roleRepo.findByName("ROLE_USER");
        if(role == null)
            throw new IllegalStateException("Could not find 'ROLE_USER' role.");
        user.getRoles().add(role);

        userRepo.save(user);

        return user;
    }

    public User signUp(@NonNull String username, @NonNull String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        signUp(user);

        return user;
    }
}
