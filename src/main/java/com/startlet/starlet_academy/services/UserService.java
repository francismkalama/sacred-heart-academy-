package com.startlet.starlet_academy.services;


import com.startlet.starlet_academy.models.User;
import com.startlet.starlet_academy.models.UserDTO;
import com.startlet.starlet_academy.models.Users;
import com.startlet.starlet_academy.repositorys.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void registerUser(User requestUser) {
        String encryptedPassword = passwordEncoder.encode(requestUser.getPassword());
        Users user = new Users();
        user.setUsername(requestUser.getUsername());
        user.setPassword(encryptedPassword);
        user.setProfile_status(true);
        user.setUser_role(requestUser.getRole().toLowerCase());
        userRepository.save(user);
    }

    public ResponseEntity<String> authenticate(String username, String rawPassword) {
        Optional<Users> userOpt = userRepository.findByUsername(username);
//        Optional<Users> userOpt = userRepository.findByActiveUserProfile(username);
        if (userOpt.isPresent()) {
            if(!userOpt.get().isProfile_status()) {
                return ResponseEntity.status(401).body("User is inactive");
            }
            if(passwordEncoder.matches(rawPassword, userOpt.get().getPassword())){
                return ResponseEntity.ok("Login successful");
            }else{
                log.info("Invalid username or password");
                return ResponseEntity.status(401).body("Invalid username or password");
            }
        }else{
            return ResponseEntity.status(401).body("Invalid username or password");
        }
//        return false;
    }
    public Page<UserDTO> getUsers(Pageable pageable) {
        return userRepository.findAllUsers(pageable).map(records -> {
            UserDTO userResponseDTO = new UserDTO();
            userResponseDTO.setId((Long) records[0]);
            userResponseDTO.setUsername((String) records[1]);
            userResponseDTO.setRole((String) records[2]);
            userResponseDTO.setProfileStatus((Boolean) records[3]);
            return  userResponseDTO;
        });
    }
    @Transactional
    public void deactivateUser(String username) {
        userRepository.deactivateUser(username);
    }
}
