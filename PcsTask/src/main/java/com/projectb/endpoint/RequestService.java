package com.projectb.endpoint;

import com.projectb.abs.AbsTask;
import com.projectb.entities.Request;
import com.projectb.repo.BasicRepo;
import com.projectb.repositories.RequestRepo;
import com.projectb.vault.AbsRestVault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "requests", produces = "application/json")
public class RequestService extends AbsRestVault<Request> {
    @Autowired
    private RequestRepo requestRepo;

    @Override
    public BasicRepo<Request> provideRepo() {
        return requestRepo;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Request insert(@RequestBody Request request) {
        requestRepo.save(request);

//        Request r = new Request();
//        r.setTitle("Title");

        return request;
    }

    @RequestMapping(value = "/foobar", method = RequestMethod.GET)
    public void foobar() {

    }
}
