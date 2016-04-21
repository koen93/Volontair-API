package com.projectb.repositories;

import com.projectb.entities.Role;
import com.projectb.repo.BasicRepo;

public interface RoleRepo extends BasicRepo<Role> {
    Role findByName(String name);
}
