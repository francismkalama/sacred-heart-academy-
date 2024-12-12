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
        user.setName(requestUser.getName());
        user.setUser_role(requestUser.getRole().toLowerCase());
        userRepository.save(user);
    }

    public ResponseEntity<String> authenticate(String username, String rawPassword) {
        log.info("Authenticating User");
        Optional<Users> userOpt = userRepository.findByUsername(username);
//        Optional<Users> userOpt = userRepository.findByActiveUserProfile(username);
        if (userOpt.isPresent()) {
            if (!userOpt.get().isProfile_status()) {
                log.info("User is inactive");
                return ResponseEntity.status(401).body("User is inactive");
            }
            if (passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
                log.info("Login successful");
                return ResponseEntity.ok("Login successful");
            } else {
                log.info("Invalid username or password");
                return ResponseEntity.status(401).body("Invalid username or password");
            }
        } else {
            log.info("Invalid username or password");
            return ResponseEntity.status(401).body("Invalid username or password");
        }
//        return false;
    }

    public Page<UserDTO> getUsers(Pageable pageable) {
        log.info("Getting List of users");
        return userRepository.findAllUsers(pageable).map(records -> {
            UserDTO userResponseDTO = new UserDTO();
            userResponseDTO.setId((Long) records[0]);
            userResponseDTO.setUsername((String) records[1]);
            userResponseDTO.setRole((String) records[2]);
            userResponseDTO.setProfileStatus((Boolean) records[3]);
            userResponseDTO.setName((String) records[4]);
            return userResponseDTO;
        });
    }

    @Transactional
    public void deactivateUser(String username) {
        userRepository.deactivateUser(username);
    }

    public long getUsersCount() {
        return userRepository.count();
    }
    @Transactional
    public UserDTO updateUser(long userId, User updateUser) {
        String encryptedPassword = passwordEncoder.encode(updateUser.getPassword());
        UserDTO userDTO = new UserDTO();
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
            user.setName(updateUser.getName());
            user.setPassword(encryptedPassword);
            user.setUser_role(updateUser.getRole());
            try{

                userRepository.save(user);
            }catch (Exception e){
                log.error("User update error {}",e.getMessage());
            }
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setUsername(user.getUsername());
            userDTO.setProfileStatus(user.isProfile_status());
            userDTO.setRole(user.getUser_role());
        return userDTO;
    }

    public UserDTO getUser(long userId) {
        UserDTO userDTO = new UserDTO();
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()){
            Users users = user.get();
            userDTO.setName(users.getName());
            userDTO.setUsername(users.getUsername());
            userDTO.setProfileStatus(users.isProfile_status());
            userDTO.setRole(users.getUser_role());
        }
        return userDTO;
    }
}
