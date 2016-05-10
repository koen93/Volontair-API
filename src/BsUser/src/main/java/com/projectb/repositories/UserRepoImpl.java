package com.projectb.repositories;

import com.projectb.entities.Account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserRepoImpl implements UserRepoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account merge(Account account) {
        return entityManager.merge(account);
    }
}
