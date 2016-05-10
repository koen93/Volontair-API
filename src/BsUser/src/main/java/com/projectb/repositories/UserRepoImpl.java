package com.projectb.repositories;

import com.projectb.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserRepoImpl implements UserRepoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User merge(User user) {
        return entityManager.merge(user);
    }
}
