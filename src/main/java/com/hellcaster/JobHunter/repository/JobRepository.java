package com.hellcaster.JobHunter.repository;

import com.hellcaster.JobHunter.entities.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j JOIN j.skillsRequired sr WHERE sr IN :skills AND LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    Page<Job> findBySkillsRequiredContainingAndLocationContainingIgnoreCase(List<String> skills, String location, Pageable pageable);
    @Query("SELECT j FROM Job j JOIN j.skillsRequired sr WHERE sr IN :skills")
    Page<Job> findBySkillsRequiredContaining(@Param("skills") List<String> skills, Pageable pageable);
    Page<Job> findByLocationContainingIgnoreCase(String location, Pageable pageable);
}
