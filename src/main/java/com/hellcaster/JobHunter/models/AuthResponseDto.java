package com.hellcaster.JobHunter.models;

import com.hellcaster.JobHunter.entities.enums.AuthStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private String token;
    private AuthStatus status;

}
