package com.projectb.endpoint;

import com.projectb.repositories.RequestRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "requests", collectionResourceRel = "requests", itemResourceRel = "requests")
public interface RequestService extends RequestRepo {
    @RestResource(path = "open", rel = "open")
    Page findByClosedIsFalse(Pageable p);

    @RestResource(path = "closed", rel = "closed")
    Page findByClosedIsTrue(Pageable p);
}
