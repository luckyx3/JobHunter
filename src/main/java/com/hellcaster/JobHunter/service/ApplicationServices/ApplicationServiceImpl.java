package com.hellcaster.JobHunter.service.ApplicationServices;

import com.hellcaster.JobHunter.entities.Application;
import com.hellcaster.JobHunter.entities.Job;
import com.hellcaster.JobHunter.entities.User;
import com.hellcaster.JobHunter.entities.enums.Status;
import com.hellcaster.JobHunter.repository.ApplicationRepository;
import com.hellcaster.JobHunter.repository.JobRepository;
import com.hellcaster.JobHunter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Log4j2
public class ApplicationServiceImpl implements ApplicationService{

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public Application submitApplication(Long userId, Long jobId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));
        Application application = Application.builder()
                .user(user)
                .job(job)
                .applicationDate(LocalDate.now())
                .status(Status.SUBMITTED)
                .build();
        return applicationRepository.save(application);
    }

    @Override
    public Application updateApplicationStatus(Long applicationId, Status status) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);
        return applicationRepository.save(application);
    }
}
