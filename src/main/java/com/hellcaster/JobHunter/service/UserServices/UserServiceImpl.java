package com.hellcaster.JobHunter.service.UserServices;

import com.hellcaster.JobHunter.entities.User;
import com.hellcaster.JobHunter.models.SignUpRequestDto;
import com.hellcaster.JobHunter.models.UpdateUserDto;
import com.hellcaster.JobHunter.repository.UserRepository;
import com.hellcaster.JobHunter.service.UserServices.UserService;
import com.hellcaster.JobHunter.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String signup(SignUpRequestDto dto) {
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if(!user.isEmpty()){
            throw new RuntimeException("User already exists");
        }
        var encodedPassword = passwordEncoder.encode(dto.getPassword());
        User newUser = User.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .role(dto.getRole())
                .name(dto.getName())
                .experience(dto.getExperience())
                .skills(dto.getSkills())
                .build();
        userRepository.save(newUser);

        return JwtUtils.generateToken(newUser.getEmail());
    }

    @Override
    public String login(String email, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authenticate = authenticationManager.authenticate(authToken);
        String username = ((UserDetails)(authenticate.getPrincipal())).getUsername();
        return JwtUtils.generateToken(username);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> updateUser(Long id, UpdateUserDto updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setRole(updatedUser.getRole());
            user.setName(updatedUser.getName());
            user.setExperience(updatedUser.getExperience());
            user.setSkills(updatedUser.getSkills());
            userRepository.save(user);
            return optionalUser;
        }
        return Optional.empty();
    }


}
