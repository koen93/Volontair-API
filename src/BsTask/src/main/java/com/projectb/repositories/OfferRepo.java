package com.projectb.repositories;

import com.projectb.entities.Offer;
import com.projectb.entities.Request;
import com.projectb.repo.CRUDRepo;
import com.projectb.repositories.abs.TaskRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@RepositoryRestResource(path = "offers", collectionResourceRel = "offers", itemResourceRel = "offers")
@Component
public interface OfferRepo extends TaskRepo<Offer> {
    @RestResource(path = "open", rel = "open")
    Page findByClosedIsFalse(Pageable p);

    @RestResource(path = "closed", rel = "closed")
    Page findByClosedIsTrue(Pageable p);
}
