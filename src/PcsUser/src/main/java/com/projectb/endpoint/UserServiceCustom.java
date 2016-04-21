package com.projectb.endpoint;

import com.projectb.entities.User;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface UserServiceCustom {
    User merge(User user);
}
