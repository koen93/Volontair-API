package com.projectb.endpoint;

import com.projectb.entities.Offer;
import com.projectb.repositories.OfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "offers", produces = "application/json")
public class OfferService extends TaskService<Offer> {


    @Autowired
    private OfferRepo offerRepo;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Offer insertOrUpdate(@RequestBody Offer offer) {
        super.provideRepo().save(offer);
        return offer;
    }

    @RequestMapping(value= "open", method = RequestMethod.GET)
    public List<Offer> showOpenTasks() {
        return offerRepo.findAllOpen();
    }

    @RequestMapping(value= "closed", method = RequestMethod.GET)
    public List<Offer> showClosedTasks() {
        return offerRepo.findAllClosed();
    }
}
