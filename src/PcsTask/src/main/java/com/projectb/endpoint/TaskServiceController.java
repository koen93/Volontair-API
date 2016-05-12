package com.projectb.endpoint;

import com.projectb.abs.AbsTask;
import com.projectb.auth.PrincipalService;
import com.projectb.entities.User;
import com.projectb.exception.ResourceNotOwnedByPrincipalException;
import com.projectb.repositories.abs.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TaskServiceController<T extends AbsTask> {
    private static final EmbeddedWrappers WRAPPERS = new EmbeddedWrappers(false);
    private static final double R = 6372.8; // Haversine constant formula in kilometers

    @Autowired
    private PrincipalService principalService;

    public ResponseEntity<Resources<?>> findWithinRadius(PersistentEntityResourceAssembler assembler, Class<? extends T> type, double lat, double lng, int radius) {

        List<T> tasksBasedByRadius = new ArrayList<>();
        List<T> tasks = provideRepo().findAll();
        if(tasks != null) {
            tasksBasedByRadius = findEstablishmentsWithinRadius(tasks, lat, lng, radius);
        }

        return ResponseEntity.ok(entitiesToResource(tasksBasedByRadius, assembler, type));
    }

    public List<T> findEstablishmentsWithinRadius(List<T> list, double lat, double lng, double radius) {
        return list.stream().filter(e -> haversine(lat, lng, e.getLatitude(), e.getLongitude()) <= radius).collect(Collectors.toList());
    }

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
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
