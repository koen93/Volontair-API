package com.projectb.endpoint;

import com.projectb.abs.AbsTask;
import com.projectb.repo.CRUDRepo;
import com.projectb.repositories.abs.TaskRepo;
import com.projectb.vault.AbsCRUDRestVault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public abstract class TaskService<T extends AbsTask> extends AbsCRUDRestVault<T> {

    @RequestMapping(value= "{id}/close", method = RequestMethod.PUT)
    public ResponseEntity<String> closeTask(@PathVariable Long id) {
        T task = taskRepo.findOne(id);
        if(task != null) {
            task.setClosed(true);
            taskRepo.save(task);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value= "{id}/reopen", method = RequestMethod.PUT)
    public ResponseEntity<String> reopenTask(@PathVariable Long id) {
        T task = taskRepo.findOne(id);
        if(task != null) {
            task.setClosed(false);
            taskRepo.save(task);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Autowired
    private TaskRepo<T> taskRepo;

    @Override
    public CRUDRepo<T> provideRepo() {
        return taskRepo;
    }

}
