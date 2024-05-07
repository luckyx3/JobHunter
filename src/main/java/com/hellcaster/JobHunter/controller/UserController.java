package com.hellcaster.JobHunter.controller;

import com.hellcaster.JobHunter.entities.User;
import com.hellcaster.JobHunter.entities.enums.AuthStatus;
import com.hellcaster.JobHunter.models.AuthRequestDto;
import com.hellcaster.JobHunter.models.AuthResponseDto;
import com.hellcaster.JobHunter.models.SignUpRequestDto;
import com.hellcaster.JobHunter.models.UpdateUserDto;
import com.hellcaster.JobHunter.service.UserServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER', 'ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updatedUser) {
        Optional<User> user = userService.updateUser(id, updatedUser);
//        if (user.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return new ResponseEntity<>(user.get(), HttpStatus.OK);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
}
