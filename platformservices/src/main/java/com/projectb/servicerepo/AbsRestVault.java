package com.projectb.servicerepo;

import com.projectb.abs.AbsEntity;
import com.projectb.repo.BasicRepo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class AbsRestVault<E extends AbsEntity> {

    private BasicRepo<E> repo;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public E getById(@PathVariable("id") final long id)  {
        E foundedEntity = repo.findOne(id);
        if (foundedEntity == null) {
            throw new EntityNotFoundException("Entity not found");
        }
        return foundedEntity;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<E> getAll() {
        return repo.findAll();
    }
}
