package com.projectb.endpoint;

import com.projectb.abs.AbsTask;
import com.projectb.repo.BasicRepo;
import com.projectb.repositories.abs.TaskRepo;
import com.projectb.vault.AbsRestVault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class TaskService<T extends AbsTask> extends AbsRestVault<T> {

    @RequestMapping(value= "{id}/close", method = RequestMethod.PUT)
    public ResponseEntity<String> closeRequest(@PathVariable Long id) {
        T task = taskRepo.findOne(id);
        if(task != null) {
            close(task);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    protected void close(T task) {
        task.setClosed(true);
        taskRepo.save(task);
    }

    @Autowired
    private TaskRepo<T> taskRepo;

    @Override
    public BasicRepo<T> provideRepo() {
        return taskRepo;
    }

}
