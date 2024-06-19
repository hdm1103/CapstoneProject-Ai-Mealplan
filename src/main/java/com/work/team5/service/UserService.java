package com.work.team5.service;

import com.work.team5.dto.UserDto;
import com.work.team5.model.User;
import com.work.team5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByUserid(String userid) {
        return userRepository.findByUserid(userid);
    }

    public User registerUser(UserDto userDto) {
        if (userRepository.findByUserid(userDto.getUserid()) != null) {
            return null; // User already exists
        }
        User user = new User();
        user.setUserid(userDto.getUserid());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        return userRepository.save(user);
    }
}
