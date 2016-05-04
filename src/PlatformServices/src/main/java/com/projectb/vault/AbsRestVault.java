package com.projectb.vault;

import com.projectb.abs.AbsEntity;
import com.projectb.repo.BasicRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;


//TODO: Create our own exceptions for the platform
@CrossOrigin
public abstract class AbsRestVault<E extends AbsEntity> {

    private BasicRepo<E> repo;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public E getById(@PathVariable("id") final Long id)  {
        E foundedEntity = repo.findOne(id);
        if (foundedEntity == null) {
            throw new EntityNotFoundException("Entity not found");
        }
        return foundedEntity;
    }

    @PostConstruct
    public void fillValues() {
        this.repo = provideRepo();
    }

    //TODO: Implement update

    //TODO: Is it smart to add delete to this abstract layer? When do we want to use it?


    //TODO: However this one could be not implemented if you don't require more operations...
    public abstract BasicRepo<E> provideRepo();
}
