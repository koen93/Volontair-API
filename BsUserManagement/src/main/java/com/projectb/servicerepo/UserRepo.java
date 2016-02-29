package com.projectb.servicerepo;

import com.projectb.entities.User;
import com.projectb.repo.BasicRepo;
import org.springframework.stereotype.Component;

//TODO: Set up JPA/Hibernate in starter application.properties to get this working
@Component
public interface UserRepo {

    User findByUsername(final String username);
}
