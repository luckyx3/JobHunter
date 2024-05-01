package com.hellcaster.JobHunter.controller;

import com.hellcaster.JobHunter.entities.User;
import com.hellcaster.JobHunter.entities.enums.AuthStatus;
import com.hellcaster.JobHunter.models.AuthRequestDto;
import com.hellcaster.JobHunter.models.AuthResponseDto;
import com.hellcaster.JobHunter.models.SignUpRequestDto;
import com.hellcaster.JobHunter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody SignUpRequestDto dto) {
        try {
            String jwtToken = userService.signup(dto);
            AuthResponseDto authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.USER_CREATED_SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.CREATED).body(authResponseDto);
        } catch (Exception e) {
            AuthResponseDto authResponseDto = new AuthResponseDto(null, AuthStatus.USER_ALREADY_EXISTS);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(authResponseDto);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        var jwtToken = userService.login(dto.getEmail(), dto.getPassword());
        AuthResponseDto authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.LOGIN_SUCCESS);
        return ResponseEntity.ok(authResponseDto);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        User user = userService.getUserById(id);
//        return ResponseEntity.ok(user);
//    }

//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
//        User user = userService.updateUser(id, updatedUser);
//        return ResponseEntity.ok(user);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
}
