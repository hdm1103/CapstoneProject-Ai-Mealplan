package com.work.team5.repository;

import com.work.team5.model.Prompt;
import com.work.team5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {
    List<Prompt> findByUser(User user);
}
