package com.projectb.endpoint;

import com.projectb.entities.Category;
import com.projectb.repositories.CategoryRepo;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "categories", collectionResourceRel = "categories", itemResourceRel = "categories")
public interface CategoriesService extends CategoryRepo {
    @Override
    @RestResource(exported = false)
    <S extends Category> S save(S s);

    @Override
    @RestResource(exported = false)
    void delete(Long id);
}
