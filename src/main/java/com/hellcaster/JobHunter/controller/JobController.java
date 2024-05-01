package com.hellcaster.JobHunter.controller;

import com.hellcaster.JobHunter.entities.Job;
import com.hellcaster.JobHunter.models.CreateJob;
import com.hellcaster.JobHunter.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping("/search")
    public ResponseEntity<Page<Job>> searchJobsBySkillsAndLocation(
            @RequestParam(required = false) List<String> skills,
            @RequestParam(required = false) String location,
            Pageable pageable
    ) {
        Page<Job> jobs;
        if(skills != null && !skills.isEmpty() && location != null && !location.isEmpty()) {
            jobs = jobService.searchJobsBySkillsAndLocation(skills, location, pageable);
        }
        else if (skills != null && !skills.isEmpty()) {
            jobs = jobService.searchJobsBySkills(skills, pageable);
        }
        else if (location != null && !location.isEmpty()) {
            jobs = jobService.searchJobsByLocation(location, pageable);
        }
        else {
            jobs = jobService.findAll(pageable);
        }
        return ResponseEntity.ok(jobs);
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody CreateJob createJob) {
        Job savedJob = jobService.addJob(createJob);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }
}
