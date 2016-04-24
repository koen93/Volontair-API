package com.projectb.repositories;

import com.projectb.entities.Conversation;
import com.projectb.repo.BasicRepo;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepo extends BasicRepo<Conversation> {

}
