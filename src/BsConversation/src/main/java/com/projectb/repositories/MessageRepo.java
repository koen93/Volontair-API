package com.projectb.repositories;

import com.projectb.entities.Message;
import com.projectb.repo.BasicRepo;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends BasicRepo<Message> {

}
