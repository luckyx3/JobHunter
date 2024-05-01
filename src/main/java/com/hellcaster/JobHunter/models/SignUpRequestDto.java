package com.hellcaster.JobHunter.models;

import com.hellcaster.JobHunter.entities.enums.Role;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String email;
    private String password;
    private String name;
    private List<String> skills;
    private Role role;
    private float experience;
}
