package com.projectb.endpoint;

import com.projectb.entities.User;
import com.projectb.repositories.UserRepo;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "users", collectionResourceRel = "users", itemResourceRel = "users")
public interface UserService extends UserRepo, UserServiceCustom {

}
