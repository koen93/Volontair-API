package com.projectb.endpoint;

import com.projectb.entities.Report;
import com.projectb.repositories.ReportRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "reports", collectionResourceRel = "reports", itemResourceRel = "reports")
public interface ReportService extends ReportRepo {
    @Override
    @RestResource(exported = false)
    Page<Report> findAll(Pageable pageable);

    @Override
    @RestResource(exported = false)
    void delete(Long id);
}
