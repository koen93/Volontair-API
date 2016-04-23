package com.projectb.endpoint;

import com.projectb.repositories.ConversationRepo;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "conversations", collectionResourceRel = "conversations", itemResourceRel = "conversations")
public interface ConversationService extends ConversationRepo {

}
