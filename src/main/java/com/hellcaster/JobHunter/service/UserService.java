package com.hellcaster.JobHunter.service;

import com.hellcaster.JobHunter.entities.User;
import com.hellcaster.JobHunter.models.AuthRequestDto;
import com.hellcaster.JobHunter.models.SignUpRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserService {

    String signup(SignUpRequestDto dto);

    String login(String email, String password);
}
