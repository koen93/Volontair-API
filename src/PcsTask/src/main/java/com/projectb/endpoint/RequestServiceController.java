package com.projectb.endpoint;

import com.projectb.entities.Request;
import com.projectb.repositories.RequestRepo;
import com.projectb.repositories.abs.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RepositoryRestController
public class RequestServiceController extends TaskServiceController<Request> {
    @Autowired
    private RequestRepo requestRepo;

    @RequestMapping(value= "/requests/search/findWithinRadius", method = RequestMethod.GET)
    public ResponseEntity<List<Request>> findWithinRadius(double lat, double lng, int radius) {
        return super.findWithinRadius(lat, lng, radius);
    }

    @RequestMapping(value= "/requests/{id}/close", method = RequestMethod.PUT)
    public ResponseEntity<?> closeTask(@PathVariable Long id) {
        return super.closeTask(id);
    }

    @RequestMapping(value= "/requests/{id}/reopen", method = RequestMethod.PUT)
    public ResponseEntity<?> reopenTask(@PathVariable Long id) {
        return super.reopenTask(id);
    }

    @Override
    public TaskRepo<Request> provideRepo() {
        return requestRepo;
    }
}
