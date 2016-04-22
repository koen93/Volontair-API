package com.projectb.endpoint;

import com.projectb.entities.User;
import com.projectb.repositories.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "users", collectionResourceRel = "users", itemResourceRel = "users")
public interface UserService extends UserRepo, UserServiceCustom {
    @Override
    @RestResource(exported = false)
    Page<User> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    void delete(Long id);

    @Override
    @RestResource(exported = false)
    <S extends User> S save(S entity);
}
