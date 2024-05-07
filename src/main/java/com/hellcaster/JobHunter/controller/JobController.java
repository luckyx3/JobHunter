package com.hellcaster.JobHunter.controller;

import com.hellcaster.JobHunter.entities.Job;
import com.hellcaster.JobHunter.models.CreateJob;
import com.hellcaster.JobHunter.service.JobServices.JobService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@Log4j2
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping("/search")
    public ResponseEntity<Page<Job>> searchJobsBySkillsAndLocation(
            @RequestParam(required = false) List<String> skills,
            @RequestParam(required = false) String location,
            Pageable pageable
    ) {
        log.info("Search jobs called with skills: " + skills + " and location: " + location);
        Page<Job> jobs;
        if(skills != null && !skills.isEmpty() && location != null && !location.isEmpty()) {
            log.info("Search jobs by skill and location is called");
            jobs = jobService.searchJobsBySkillsAndLocation(skills, location, pageable);
        }
        else if (skills != null && !skills.isEmpty()) {
            log.info("Search jobs by skill is called");
            jobs = jobService.searchJobsBySkills(skills, pageable);
        }
        else if (location != null && !location.isEmpty()) {
            log.info("Search jobs by location is called");
            jobs = jobService.searchJobsByLocation(location, pageable);
        }
        else {
            log.info("Search all jobs is called");
            jobs = jobService.findAll(pageable);
        }
        return ResponseEntity.ok(jobs);
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody CreateJob createJob) {
        log.info("Create job called with job: " + createJob);
        Job savedJob = jobService.addJob(createJob);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }
}
