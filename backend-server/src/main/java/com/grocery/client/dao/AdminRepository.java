package com.grocery.client.dao;

import com.grocery.client.entity.Admin;
import com.grocery.client.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByEmail(String email);

}
