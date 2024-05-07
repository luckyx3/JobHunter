package com.hellcaster.JobHunter.service.ApplicationServices;

import com.hellcaster.JobHunter.entities.Application;
import com.hellcaster.JobHunter.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationService {
    Application submitApplication(Long userId, Long jobId);

//    Page<Application> getApplicationsByJobId(Long jobId, Pageable pageable);
    Application updateApplicationStatus(Long applicationId, Status status);
}
