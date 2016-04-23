package com.projectb.endpoint;

import com.projectb.auth.PrincipalService;
import com.projectb.entities.Report;
import com.projectb.entities.User;
import com.projectb.exception.MethodNotAllowedException;
import com.projectb.exception.ResourceNotOwnedByPrincipalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ReportEventHandler {
    @Autowired
    private PrincipalService principalService;

    @HandleBeforeCreate
    public void handleBeforeCreate(Report report) {
        attachReporter(report);
    }

    @HandleBeforeSave
    public void handleBeforeSave(Report report) {
        throw new MethodNotAllowedException();
    }

    private void attachReporter(Report report) {
        report.setReporter(principalService.getAuthenticatedUser());
    }
}
