package com.projectb.repositories;

import com.projectb.entities.Category;
import com.projectb.entities.Conversation;
import com.projectb.entities.Account;
import com.projectb.repo.BasicRepo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "conversations", collectionResourceRel = "conversations", itemResourceRel = "conversations")
@Repository
public interface ConversationRepo extends BasicRepo<Conversation> {
    @Query("select c from Conversation c where c.listener = :listener or c.starter = :starter")
    List<Conversation> findWhereListenerOrStarter(@Param("listener") Account listener, @Param("starter") Account starter);

    // TODO: Only apply category filter on OTHER user, not on the currently authenticated user
    @Query("select c from Conversation c where (c.listener = :listener or c.starter = :starter) and (:category member of c.listener.categories or :category member of c.starter.categories)")
    List<Conversation> findWhereListenerOrStarterAndHasCategory(@Param("listener") Account listener, @Param("starter") Account starter, @Param("category") Category category);
}
