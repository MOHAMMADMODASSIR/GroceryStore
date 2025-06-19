package com.grocery.client.service.impl;

import com.grocery.client.dao.AdminRepository;
import com.grocery.client.entity.Admin;
import com.grocery.client.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public boolean searchAdmin(Admin admin) {
        Admin theAdmin = adminRepository.findById(admin.getId()).orElse(null);
        if(theAdmin!=null) return true;
        return false;
    }

    @Override
    public Admin searchAdminByEmail(String email) {
        Admin admin = adminRepository.findByEmail(email);
        if(admin!=null) return admin;
        return null;
    }
}
