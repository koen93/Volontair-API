package com.projectb.endpoint;

import com.projectb.abs.AbsTask;
import com.projectb.entities.Request;
import com.projectb.repo.BasicRepo;
import com.projectb.repositories.abs.TaskRepo;
import com.projectb.vault.AbsRestVault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;

public abstract class TaskService<T extends AbsTask> extends AbsRestVault<T> {

    @Autowired
    private TaskRepo<T> taskRepo;

    @Override
    public BasicRepo<T> provideRepo() {
        return taskRepo;
    }

}
