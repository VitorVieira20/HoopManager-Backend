package com.hoopmanger.api.services;

import com.hoopmanger.api.domain.user.User;
import com.hoopmanger.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    @Autowired
    private UserRepository userRepository;
}
