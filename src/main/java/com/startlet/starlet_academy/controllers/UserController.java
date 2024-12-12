package com.startlet.starlet_academy.controllers;

import com.startlet.starlet_academy.models.User;
import com.startlet.starlet_academy.models.UserDTO;
import com.startlet.starlet_academy.services.UserService;
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
    public  ResponseEntity<Page<UserDTO>> getUsers(@PageableDefault(size = 100,sort = "id")Pageable pageable){
        Page<UserDTO> users = userService.getUsers(pageable);
        return ResponseEntity.ok(users);
    }
    @PutMapping("/{username}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable String username) {
        userService.deactivateUser(username);
        return ResponseEntity.ok("User deactivated successfully.");
    }

}
