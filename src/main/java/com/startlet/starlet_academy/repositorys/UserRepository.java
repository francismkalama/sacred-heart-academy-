package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
//    @Query(value = "SELECT * FROM Users u WHERE u.username = :username AND u.profile_status = 'true' ",nativeQuery = true)
//    Optional<Users>findByActiveUserProfile(String username);
    @Query(value = "SELECT id,username,user_role,profile_status,name FROM users",nativeQuery = true)
    Page<Object[]> findAllUsers(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Users SET profile_status = 'false' WHERE username = :username",nativeQuery = true)
    void deactivateUser(@Param("username") String username);
}
