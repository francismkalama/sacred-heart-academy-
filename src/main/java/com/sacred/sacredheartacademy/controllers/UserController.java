package com.sacred.sacredheartacademy.controllers;

import com.sacred.sacredheartacademy.models.Student;
import com.sacred.sacredheartacademy.models.User;
import com.sacred.sacredheartacademy.models.UserDTO;
import com.sacred.sacredheartacademy.models.Users;
import com.sacred.sacredheartacademy.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password) {
        public ResponseEntity<String> register(@Valid @RequestBody User user) {
logger.info("Registration request {}",user.getUsername());
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody User user) {
        return userService.authenticate(user.getUsername(), user.getPassword());
    }
    @GetMapping("user_list")
    public  ResponseEntity<Page<UserDTO>> getUsers(@PageableDefault(size =1000,sort = "id")Pageable pageable){
        Page<UserDTO> users = userService.getUsers(pageable);
        return ResponseEntity.ok(users);
    }
    @PostMapping("/{username}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable String username) {
        userService.deactivateUser(username);
        return ResponseEntity.ok("User deactivated successfully.");
    }
    @PostMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long userId, @RequestBody User user){
        UserDTO userUpdateResponse = userService.updateUser(userId,user);
        return ResponseEntity.ok(userUpdateResponse);
    }

    @GetMapping("user_count")
    public  ResponseEntity<Long> getUsersCount(){
        long  countOfusers = userService.getUsersCount();
        return ResponseEntity.ok(countOfusers);
    }
    @GetMapping("/{userId}")
    public  ResponseEntity<UserDTO> getUser(@PathVariable long userId){
        UserDTO  countOfusers = userService.getUser(userId);
        return ResponseEntity.ok(countOfusers);
    }

}
