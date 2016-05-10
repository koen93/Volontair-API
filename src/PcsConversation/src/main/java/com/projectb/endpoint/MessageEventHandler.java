package com.projectb.endpoint;

import com.projectb.auth.PrincipalService;
import com.projectb.entities.Account;
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
        Account authenticatedAccount = principalService.getAuthenticatedUser();
        if(!message.getSender().getId().equals(authenticatedAccount.getId()))
            throw new ResourceNotOwnedByPrincipalException();
    }

    @HandleBeforeCreate
    public void checkPostPrivileges(Message message) {
        Account authenticatedAccount = principalService.getAuthenticatedUser();
        message.setSender(authenticatedAccount);
        if(!message.getConversation().getListener().getId().equals(authenticatedAccount.getId())
        && !message.getConversation().getStarter().getId().equals(authenticatedAccount.getId())) {
            throw new ResourceNotOwnedByPrincipalException();
        }
    }
}
