package com.hellcaster.JobHunter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateJob {
    private String title;
    private String company;
    private String location;
    private String description;
    private List<String> skillsRequired;
}
