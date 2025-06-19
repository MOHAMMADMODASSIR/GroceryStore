package com.grocery.client.service.impl;

import com.grocery.client.dao.UserRepository;
import com.grocery.client.entity.User;
import com.grocery.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean searchUser(User user) {
        String theEmail = user.getEmail();
        User userFromDBEmail = userRepository.findByEmail(theEmail);
        if(userFromDBEmail != null) {
            return true;
        }
        return false;
    }

    @Override
    public User searchUserByEmail(String theEmail) {
        User userFromDBEmail = userRepository.findByEmail(theEmail);
        if(userFromDBEmail != null) {
            return userFromDBEmail;
        }
        return null;
    }
}
