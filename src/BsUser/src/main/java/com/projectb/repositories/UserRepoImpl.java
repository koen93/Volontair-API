package com.projectb.repositories;

import com.projectb.entities.Goal;
import com.projectb.entities.User;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class UserRepoImpl implements UserRepoCustom {
    private static final double EARTH_RADIUS = 6371000;

    @PersistenceContext
    private EntityManager entityManager;

    private UserRepo userRepo;

    @Override
    public User merge(User user) {
        return entityManager.merge(user);
    }

    @Override
    public List<User> findWithinBounds(double northEastLat, double northEastLng, double southWestLat, double southWestLng, Goal goal) {
        Query query = entityManager.createQuery("SELECT u from User u where (u.latitude >= :southLat AND u.latitude <= :northLat) AND (u.longitude >= :westLng AND u.longitude <= :eastLng) AND (u.goal = :goalBoth OR u.goal = :goal)");
        query.setParameter("northLat", northEastLat);
        query.setParameter("southLat", southWestLat);
        query.setParameter("eastLng", northEastLng);
        query.setParameter("westLng", southWestLng);
        query.setParameter("goalBoth", Goal.GIVE_AND_GET_HELP);
        query.setParameter("goal", goal);

        return query.getResultList();
    }

    /**
     * Find users within the given radius.
     *
     * "I felt a great disturbance in the Force as if millions of GIS developers suddenly cried out in terror and were
     * suddenly silenced. I fear something terrible has happened."
     *
     * @param lat
     * @param lng
     * @param radius
     * @return all users within the given range
     */
    @Override
    public List<User> findWithinRadiusAndGoal(double lat, double lng, int radius, Goal goal) {
        double distanceInMeters = radius * 1000;

        double northEastLat = lat + Math.toDegrees(distanceInMeters / EARTH_RADIUS);
        double southWestLat = lat - Math.toDegrees(distanceInMeters / EARTH_RADIUS);
        double northEastLng = lng + Math.toDegrees(distanceInMeters / EARTH_RADIUS / Math.cos(Math.toRadians(lat)));
        double southWestLng = lng - Math.toDegrees(distanceInMeters / EARTH_RADIUS / Math.cos(Math.toRadians(lat)));

        return findWithinBounds(northEastLat, northEastLng, southWestLat, southWestLng, goal);
    }
}
