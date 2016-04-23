package com.projectb.endpoint;

import com.projectb.auth.PrincipalService;
import com.projectb.entities.Conversation;
import com.projectb.entities.User;
import com.projectb.exception.ResourceNotOwnedByPrincipalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ConversationEventHandler {
    @Autowired
    private PrincipalService principalService;

    @HandleBeforeSave
    @HandleBeforeDelete
    public void checkPrivileges(Conversation conversation) {
        User authenticatedUser = principalService.getAuthenticatedUser();
        if(!conversation.getListener().getId().equals(authenticatedUser.getId())
        && !conversation.getStarter().getId().equals(authenticatedUser.getId())) {
            throw new ResourceNotOwnedByPrincipalException();
        }
    }
}
