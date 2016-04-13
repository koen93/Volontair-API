package com.projectb.repositories.abs;

import com.projectb.abs.AbsTask;
import com.projectb.repo.BasicRepo;

public interface TaskRepo<T extends AbsTask> extends BasicRepo<T> {

}
