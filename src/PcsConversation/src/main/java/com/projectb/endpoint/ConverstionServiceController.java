package com.projectb.endpoint;

import com.projectb.auth.PrincipalService;
import com.projectb.entities.Conversation;
import com.projectb.entities.Message;
import com.projectb.entities.User;
import com.projectb.exception.ResourceNotOwnedByPrincipalException;
import com.projectb.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
public class ConverstionServiceController {
    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private PrincipalService principalService;

    @RequestMapping(method = RequestMethod.POST, value = "/conversations/{id}/message")
    public @ResponseBody ResponseEntity<?> addMessage(@PathVariable("id") long id, @RequestBody Message message) {
        User authenticatedUser = principalService.getAuthenticatedUser();
        Conversation conversation = conversationService.findOne(id);
        if(conversation == null)
            throw new ResourceNotFoundException();
        if(!conversation.getListener().getId().equals(authenticatedUser.getId())
        && !conversation.getStarter().getId().equals(authenticatedUser.getId())) {
            throw new ResourceNotOwnedByPrincipalException();
        }

        message.setSender(authenticatedUser);
        message.setConversation(conversation);
        messageRepo.save(message);

        // TODO: Return message
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
