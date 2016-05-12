package com.projectb.repositories;

import com.projectb.entities.User;
import com.projectb.repo.BasicRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Component;

@Component
@RepositoryRestResource(path = "users", collectionResourceRel = "users", itemResourceRel = "users")
public interface UserRepo extends BasicRepo<User> {
    User findByUsername(@Param("username") String username);

    @Override
//    @RestResource(exported = false)
    Page<User> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    void delete(Long id);

    @Override
    User save(User s);
}
