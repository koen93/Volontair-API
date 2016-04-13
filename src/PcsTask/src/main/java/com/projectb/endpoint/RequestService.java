package com.projectb.endpoint;

import com.projectb.abs.AbsTask;
import com.projectb.entities.Request;
import com.projectb.repo.BasicRepo;
import com.projectb.repositories.RequestRepo;
import com.projectb.vault.AbsRestVault;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "requests", produces = "application/json")
public class RequestService extends TaskService<Request> {


//    @Autowired
//    private RequestRepo requestRepo;

//    @Override
//    public BasicRepo<Request> provideRepo() {
//        return requestRepo;
//    }

    @Autowired
    private RequestRepo requestRepo;

    @RequestMapping(value= "{id}/close", method = RequestMethod.PUT)
    public ResponseEntity<String> closeRequest(@PathVariable Long id) {
        Request request = requestRepo.findOne(id);
        if(request != null) {
            close(request);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private void close(Request request) {
        request.setClosed(true);
        requestRepo.save(request);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Request insert(@RequestBody Request request) {
        super.provideRepo().save(request);
        return request;
    }

    @RequestMapping(value = "/foobar", method = RequestMethod.GET)
    public void foobar() {

    }
}
