package com.hellcaster.JobHunter.controller;

import com.hellcaster.JobHunter.entities.Application;
import com.hellcaster.JobHunter.entities.enums.Status;
import com.hellcaster.JobHunter.service.ApplicationServices.ApplicationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@Log4j2
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/{userId}/job/{jobId}")
    public ResponseEntity<Application> submitApplication(@PathVariable Long userId, @PathVariable Long jobId){
        log.info("Submitting application for user " + userId + " for job " + jobId);
        try {
            Application application = applicationService.submitApplication(userId, jobId);
            return new ResponseEntity<>(application, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{applicationId}/status/{newStatus}")
    public ResponseEntity<Application> updateApplicationStatus(@PathVariable Long applicationId, @PathVariable Status newStatus){
        log.info("Updating application status for application " + applicationId + " to " + newStatus);
        try {
            Application updatedApplication = applicationService.updateApplicationStatus(applicationId, newStatus);
            return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
