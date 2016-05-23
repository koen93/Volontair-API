package com.projectb.repositories;

import com.projectb.entities.Goal;
import com.projectb.entities.User;

import java.util.List;

public interface UserRepoCustom {
    User merge(User user);
    List<User> findWithinRadiusAndGoal(double lat, double lng, int radius, Goal goal);
}
