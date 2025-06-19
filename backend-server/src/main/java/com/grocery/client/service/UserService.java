package com.grocery.client.service;

import com.grocery.client.entity.User;

public interface UserService {

    public User createUser(User user);

    public boolean searchUser(User user);

    public User searchUserByEmail(String email);

}
