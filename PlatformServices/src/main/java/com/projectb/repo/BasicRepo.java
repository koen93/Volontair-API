package com.projectb.repo;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicRepo<T> extends JpaRepository<T, ID> {
    //TODO: Default functionality?
}
