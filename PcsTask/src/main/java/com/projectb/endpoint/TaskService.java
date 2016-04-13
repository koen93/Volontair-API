package com.projectb.endpoint;

import com.projectb.abs.AbsTask;
import com.projectb.vault.AbsRestVault;

public abstract class TaskService<T extends AbsTask> extends AbsRestVault<T> {

}
