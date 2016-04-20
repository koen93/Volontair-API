package com.projectb.endpoint;

import com.projectb.entities.Request;
import com.projectb.repositories.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "requests", produces = "application/json")
public class RequestService extends TaskService<Request> {


    @Autowired
    private RequestRepo requestRepo;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Request insertOrUpdate(@RequestBody Request request) {
        super.provideRepo().save(request);
        return request;
    }

    @RequestMapping(value= "open", method = RequestMethod.GET)
    public List<Request> showOpenTasks() {
        return requestRepo.findAllOpen();
    }

    @RequestMapping(value= "closed", method = RequestMethod.GET)
    public List<Request> showClosedTasks() {
        return requestRepo.findAllClosed();
    }
}
