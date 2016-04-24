package com.projectb.repositories;

import com.projectb.entities.Conversation;
import com.projectb.entities.Message;
import com.projectb.repo.BasicRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@RepositoryRestResource(path = "messages", collectionResourceRel = "messages", itemResourceRel = "messages")
@Repository
public interface MessageRepo extends BasicRepo<Message> {
    @Query("select m from com.projectb.entities.Message m where m.conversation = ?1 and m.created >= ?2 order by m.created")
    Set<Message> findSince(@Param("conversation") Conversation conversation, @Param("since") Timestamp date);
}
