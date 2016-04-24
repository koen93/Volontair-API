package com.projectb.repositories;

import com.projectb.entities.Category;
import com.projectb.entities.Offer;
import com.projectb.repo.BasicRepo;
import com.projectb.repo.CRUDRepo;
import com.projectb.repositories.abs.TaskRepo;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "categories", collectionResourceRel = "categories", itemResourceRel = "categories")
public interface CategoryRepo extends BasicRepo<Category> {
    @Override
    @RestResource(exported = false)
    Category save(Category s);

    @Override
    @RestResource(exported = false)
    void delete(Long id);
}
