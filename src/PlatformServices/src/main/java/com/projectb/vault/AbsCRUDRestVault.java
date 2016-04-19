package com.projectb.vault;

import com.projectb.abs.AbsEntity;
import com.projectb.repo.BasicRepo;
import com.projectb.repo.CRUDRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;


//TODO: Create our own exceptions for the platform
@CrossOrigin
public abstract class AbsCRUDRestVault<E extends AbsEntity> {

    private CRUDRepo<E> repo;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public E getById(@PathVariable("id") final Long id)  {
        E foundedEntity = repo.findOne(id);
        if (foundedEntity == null) {
            throw new EntityNotFoundException("Entity not found");
        }
        return foundedEntity;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<E> getAll() {
        return repo.findAll();
    }

    @PostConstruct
    public void fillValues() {
        this.repo = provideRepo();
    }

    public abstract CRUDRepo<E> provideRepo();
}
