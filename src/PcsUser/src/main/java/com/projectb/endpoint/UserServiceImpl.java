package com.projectb.endpoint;

import com.projectb.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserServiceImpl implements UserServiceCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User merge(User user) {
        return entityManager.merge(user);
    }
}
