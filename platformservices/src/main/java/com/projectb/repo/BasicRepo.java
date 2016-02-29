package com.projectb.repo;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;

public interface BasicRepo<T> extends CrudRepository<T, ID> {
    //TODO: Default functionality?
}
