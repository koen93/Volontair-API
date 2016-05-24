package com.projectb.endpoint;

import com.projectb.auth.PrincipalService;
import com.projectb.entities.Goal;
import com.projectb.entities.User;
import com.projectb.repositories.UserRepoCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@BasePathAwareController
public class DashboardServiceController {
    @Autowired
    private UserRepoCustom userRepo;

    @Autowired
    private PrincipalService principalService;

    @RequestMapping(value= "/dashboard", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Dashboard> dashboard(@RequestParam(required = false) Double lat, @RequestParam(required = false) Double lng, @RequestParam int radius) {
        Dashboard dashboard = new Dashboard();

        if(lat == null || lng == null) {
            User authenticatedUser = principalService.getAuthenticatedUser();
            lat = authenticatedUser.getLatitude();
            lng = authenticatedUser.getLongitude();
        }

        List<User> nearbyVolonteersWithinRange = userRepo.findWithinRadiusAndGoal(lat, lng, radius, Goal.GIVE_HELP);
        List<User> potentialContactsWithinRange = userRepo.findWithinRadiusAndGoal(lat, lng, radius, Goal.GET_HELP);

        dashboard.setNearbyVolonteers(nearbyVolonteersWithinRange.size());
        dashboard.setPotentialContacts(potentialContactsWithinRange.size());

        return ResponseEntity.ok(dashboard);
    }
}
