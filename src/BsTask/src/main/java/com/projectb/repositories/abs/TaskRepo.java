package com.projectb.repositories.abs;

import com.projectb.abs.AbsTask;
import com.projectb.entities.Category;
import com.projectb.entities.Request;
import com.projectb.repo.BasicRepo;
import com.projectb.repo.CRUDRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;

import java.util.List;

public interface TaskRepo<T extends AbsTask> extends BasicRepo<T> {
    //TODO: Implement Hibernate Spartial for searching based on lat & lng
}
