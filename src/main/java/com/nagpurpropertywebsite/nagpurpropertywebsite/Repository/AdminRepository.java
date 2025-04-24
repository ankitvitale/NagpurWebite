package com.nagpurpropertywebsite.nagpurpropertywebsite.Repository;

import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    Admin findByEmail(String email);

}