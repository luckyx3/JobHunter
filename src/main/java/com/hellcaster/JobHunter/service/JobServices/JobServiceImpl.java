package com.hellcaster.JobHunter.service.JobServices;

import com.hellcaster.JobHunter.entities.Job;
import com.hellcaster.JobHunter.models.CreateJob;
import com.hellcaster.JobHunter.repository.JobRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;
    @Override
    public Page<Job> searchJobsBySkillsAndLocation(List<String> skills, String location, Pageable pageable) {
        List<String> skillsToSearch = skills != null ? skills : List.of();
        String locationToSearch = location != null ? location.trim().toLowerCase() : "";
        Page<Job> jobs = jobRepository.findBySkillsRequiredContainingAndLocationContainingIgnoreCase(skillsToSearch, locationToSearch, pageable);
        if(jobs.hasContent()){
            return jobs;
        }
        return Page.empty();
    }

    @Override
    public Page<Job> searchJobsBySkills(List<String> skills, Pageable pageable) {
        List<String> skillsToSearch = skills != null ? skills : List.of();
        Page<Job> jobs = jobRepository.findBySkillsRequiredContaining(skillsToSearch, pageable);
        if(jobs.hasContent()){
            return jobs;
        }
        return Page.empty();
    }

    @Override
    public Page<Job> searchJobsByLocation(String location, Pageable pageable) {
        String locationToSearch = location != null ? location.trim().toLowerCase() : "";
        Page<Job> jobs = jobRepository.findByLocationContainingIgnoreCase(locationToSearch, pageable);
        if(jobs.hasContent()){
            return jobs;
        }
        return Page.empty();
    }

    @Override
    public Page<Job> findAll(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Override
    public Job addJob(CreateJob createJob) {
        Job newJob = Job.builder()
                .title(createJob.getTitle())
                .company(createJob.getCompany())
                .location(createJob.getLocation())
                .description(createJob.getDescription())
                .skillsRequired(createJob.getSkillsRequired())
                .build();

        return jobRepository.save(newJob);
    }
}
