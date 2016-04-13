package com.projectb.repositories.abs;

import com.projectb.abs.AbsTask;
import com.projectb.entities.Category;
import com.projectb.repo.BasicRepo;
import org.springframework.scheduling.config.Task;

import java.util.List;

public interface TaskRepo<T extends AbsTask> extends BasicRepo<T> {
    //TODO: Implement these later however type Category is not allowed.. :(
//    Task getTaskByCategory(Category category);
//    List<Task> getTasksInNeighborhood(double lat, double lon, double radius);
}
