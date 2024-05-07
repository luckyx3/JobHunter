package com.hellcaster.JobHunter.service.JobServices;

import com.hellcaster.JobHunter.entities.Job;
import com.hellcaster.JobHunter.models.CreateJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {
    Page<Job> searchJobsBySkillsAndLocation(List<String> skills, String location, Pageable pageable);

    Page<Job> searchJobsBySkills(List<String> skills, Pageable pageable);

    Page<Job> searchJobsByLocation(String location, Pageable pageable);

    Page<Job> findAll(Pageable pageable);

    Job addJob(CreateJob createJob);
}
