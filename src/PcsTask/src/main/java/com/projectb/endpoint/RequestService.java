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


    @Autowired
    private RequestRepo requestRepo;

//    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
//    public Request insert(@RequestBody Request request) {
//        super.provideRepo().save(request);
//        return request;
//    }

}
