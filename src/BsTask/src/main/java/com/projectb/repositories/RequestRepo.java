package com.projectb.repositories;

import com.projectb.entities.Request;
import com.projectb.repositories.abs.TaskRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface RequestRepo extends TaskRepo<Request> {
}
