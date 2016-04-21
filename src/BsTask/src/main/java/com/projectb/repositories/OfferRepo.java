package com.projectb.repositories;

import com.projectb.entities.Offer;
import com.projectb.entities.Request;
import com.projectb.repo.CRUDRepo;
import com.projectb.repositories.abs.TaskRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OfferRepo extends TaskRepo<Offer> {
    List<Offer> findAllOpen();
    List<Offer> findAllClosed();
}
