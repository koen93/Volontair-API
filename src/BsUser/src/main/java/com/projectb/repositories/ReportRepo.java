package com.projectb.repositories;

import com.projectb.entities.Report;
import com.projectb.repo.BasicRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "reports", collectionResourceRel = "reports", itemResourceRel = "reports")
public interface ReportRepo extends BasicRepo<Report> {
    @Override
    @RestResource(exported = false)
    Page<Report> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    void delete(Long id);
}
