package com.projectb.repositories;

import com.projectb.entities.User;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface UserRepoCustom {
    User merge(User user);
}
