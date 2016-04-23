package com.projectb.endpoint;

import com.projectb.repositories.abs.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController
public class RequestServiceController extends TaskServiceController {
    @Autowired
    private RequestService requestService;

    @RequestMapping(value= "/requests/{id}/close", method = RequestMethod.PUT)
    public ResponseEntity<?> closeTask(@PathVariable Long id) {
        return super.closeTask(id);
    }

    @RequestMapping(value= "/requests/{id}/reopen", method = RequestMethod.PUT)
    public ResponseEntity<?> reopenTask(@PathVariable Long id) {
        return super.reopenTask(id);
    }

    @Override
    public TaskRepo provideRepo() {
        return requestService;
    }
}
