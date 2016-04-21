package com.projectb.repositories;

import com.projectb.entities.User;
import com.projectb.repo.BasicRepo;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@Component
public interface UserRepo extends BasicRepo<User> {
    User findByUsername(String username);
}
