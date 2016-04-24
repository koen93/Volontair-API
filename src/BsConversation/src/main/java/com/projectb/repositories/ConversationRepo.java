package com.projectb.repositories;

import com.projectb.entities.Conversation;
import com.projectb.repo.BasicRepo;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "conversations", collectionResourceRel = "conversations", itemResourceRel = "conversations")
@Repository
public interface ConversationRepo extends BasicRepo<Conversation> {

}
