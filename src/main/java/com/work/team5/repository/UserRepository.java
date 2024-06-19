package com.work.team5.repository;

import com.work.team5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserid(String userid); // Corrected method name to find by user_id
}
