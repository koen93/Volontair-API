package com.projectb.endpoint;

import com.projectb.repositories.MessageRepo;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "messages", collectionResourceRel = "messages", itemResourceRel = "messages")
public interface MessageService extends MessageRepo {

}
