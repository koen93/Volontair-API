package com.projectb.endpoint;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@BasePathAwareController
public class DashboardServiceController {
    @RequestMapping(value= "/dashboard", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Dashboard> dashboard() {
        Dashboard dashboard = new Dashboard();

        return ResponseEntity.ok(dashboard);
    }
}
