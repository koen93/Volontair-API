package com.projectb.endpoint;

import com.projectb.auth.PrincipalService;
import com.projectb.entities.User;
import com.projectb.entities.Message;
import com.projectb.exception.ResourceNotOwnedByPrincipalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class MessageEventHandler {
    @Autowired
    private PrincipalService principalService;

    @HandleBeforeSave
    @HandleBeforeDelete
    public void checkPrivileges(Message message) {
        User authenticatedUser = principalService.getAuthenticatedUser();
        if(!message.getSender().getId().equals(authenticatedUser.getId()))
            throw new ResourceNotOwnedByPrincipalException();
    }

    @HandleBeforeCreate
    public void checkPostPrivileges(Message message) {
        User authenticatedUser = principalService.getAuthenticatedUser();
        message.setSender(authenticatedUser);
        if(!message.getConversation().getListener().getId().equals(authenticatedUser.getId())
        && !message.getConversation().getStarter().getId().equals(authenticatedUser.getId())) {
            throw new ResourceNotOwnedByPrincipalException();
        }
    }
}
