package com.hellcaster.JobHunter.models;

import com.hellcaster.JobHunter.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto {
    private String name;
    private List<String> skills;
    private Role role;
    private float experience;
}
