package com.projectb.repositories;

import com.projectb.entities.Request;
import com.projectb.repositories.abs.TaskRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path = "requests", collectionResourceRel = "requests", itemResourceRel = "requests")
@Component
public interface RequestRepo extends TaskRepo<Request> {
    @RestResource(path = "open", rel = "open")
    Page findByClosedIsFalse(Pageable p);

    @RestResource(path = "closed", rel = "closed")
    Page findByClosedIsTrue(Pageable p);
}
