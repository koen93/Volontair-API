package com.projectb.endpoint;

import com.projectb.abs.AbsTask;
import com.projectb.auth.PrincipalService;
import com.projectb.entities.User;
import com.projectb.exception.ResourceNotOwnedByPrincipalException;
import com.projectb.repositories.abs.TaskRepo;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TaskServiceController<T extends AbsTask> {
    private static final EmbeddedWrappers WRAPPERS = new EmbeddedWrappers(false);

    @Autowired
    private PrincipalService principalService;

    public ResponseEntity<Resources<?>> findWithinRadius(PersistentEntityResourceAssembler assembler, Class<? extends T> type, double lat, double lng, int radius) {
        List<T> tasks = provideRepo().findAll();

        // TODO: links

        return ResponseEntity.ok(entitiesToResource(tasks, assembler, type));
    }

    public ResponseEntity<?> closeTask(@PathVariable Long id) {
        T task = provideRepo().findOne(id);
        if(task == null)
            return ResponseEntity.notFound().build();
        verifyOwner(task);

        task.setClosed(true);
        provideRepo().save(task);
        return ResponseEntity.ok(task);
    }

    public ResponseEntity<?> reopenTask(@PathVariable Long id) {
        T task = provideRepo().findOne(id);
        if(task == null)
            return ResponseEntity.notFound().build();
        verifyOwner(task);

        task.setClosed(false);
        provideRepo().save(task);
        return ResponseEntity.ok(task);
    }

    private void verifyOwner(T task) {
        User user = principalService.getAuthenticatedUser();
        if(!user.getId().equals(task.getCreator().getId()))
            throw new ResourceNotOwnedByPrincipalException();
    }

    public abstract TaskRepo<T> provideRepo();

    private Resources<?> entitiesToResource(Iterable<T> entities, PersistentEntityResourceAssembler assembler, Class<? extends T> domainType) {
        if(!entities.iterator().hasNext()) {
            List<Object> content = Arrays.<Object>asList(WRAPPERS.emptyCollectionOf(domainType));
            return new Resources<Object>(content); // TODO: getDefaultSelfLink()?
        }

        List<Resource<Object>> resources = new ArrayList<>();
        for(T task : entities) {
            resources.add(task == null ? null : assembler.toResource(task));
        }

        return new Resources<Resource<Object>>(resources); // TODO: getDefaultSelfLink()?
    }
}
