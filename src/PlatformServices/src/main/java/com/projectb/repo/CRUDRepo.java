package com.projectb.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CRUDRepo<T> extends CrudRepository<T, Long> {
}
