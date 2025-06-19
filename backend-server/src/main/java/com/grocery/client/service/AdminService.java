package com.grocery.client.service;

import com.grocery.client.entity.Admin;
import com.grocery.client.entity.User;

public interface AdminService {

    public Admin createAdmin(Admin admin);

    public boolean searchAdmin(Admin admin);

    public Admin searchAdminByEmail(String email);

}
