package com.projectb.servicerepo;

import com.projectb.entities.User;
import com.projectb.repo.BasicRepo;
import org.springframework.stereotype.Component;

@Component
public interface UserRepo extends BasicRepo<User> {

    User findByUsername(final String username);
}
