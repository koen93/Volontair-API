package com.projectb.repositories;

import com.projectb.entities.Goal;
import com.projectb.entities.User;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface UserRepoCustom {
    User merge(User user);

    List<User> findWithinBounds(double northEastLat, double northEastLng, double southWestLat, double southWestLng, Goal goal);
    List<User> findWithinRadiusAndGoal(double lat, double lng, int radius, Goal goal);
}
