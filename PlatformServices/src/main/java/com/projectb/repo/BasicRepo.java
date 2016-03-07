package com.projectb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicRepo<T> extends JpaRepository<T, Long> {
}
