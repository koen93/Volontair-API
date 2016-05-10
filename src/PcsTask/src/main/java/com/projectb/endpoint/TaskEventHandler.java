package com.projectb.endpoint;

import com.projectb.abs.AbsTask;
import com.projectb.auth.PrincipalService;
import com.projectb.entities.User;
import com.projectb.exception.ResourceNotOwnedByPrincipalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class TaskEventHandler {
    @Autowired
    PrincipalService authService;

    @HandleBeforeSave
    @HandleBeforeDelete
    public void verifyOwner(AbsTask task) {
        User user = authService.getAuthenticatedUser();
        if(!user.getId().equals(task.getCreator().getId()))
            throw new ResourceNotOwnedByPrincipalException();
    }
}
