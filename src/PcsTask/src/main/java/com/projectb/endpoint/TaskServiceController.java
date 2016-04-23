package com.projectb.endpoint;

import com.projectb.abs.AbsTask;
import com.projectb.auth.PrincipalService;
import com.projectb.entities.Offer;
import com.projectb.entities.User;
import com.projectb.exception.ResourceNotOwnedByPrincipalException;
import com.projectb.repositories.abs.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class TaskServiceController<T extends AbsTask> {
    @Autowired
    private PrincipalService principalService;

    public ResponseEntity<?> closeTask(@PathVariable Long id) {
        T task = provideRepo().findOne(id);
        if(task == null)
            return ResponseEntity.notFound().build();
        verifyOwner(task);

        task.setClosed(true);
        provideRepo().save(task);
        return ResponseEntity.ok(task);
    }

    public ResponseEntity<?> reopenTask(@PathVariable Long id) {
        T task = provideRepo().findOne(id);
        if(task == null)
            return ResponseEntity.notFound().build();
        verifyOwner(task);

        task.setClosed(false);
        provideRepo().save(task);
        return ResponseEntity.ok(task);
    }

    private void verifyOwner(T task) {
        User user = principalService.getAuthenticatedUser();
        if(!user.getId().equals(task.getCreator().getId()))
            throw new ResourceNotOwnedByPrincipalException();
    }

    public abstract TaskRepo<T> provideRepo();
}
