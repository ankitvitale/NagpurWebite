package com.nagpurpropertywebsite.nagpurpropertywebsite.Service;

import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Admin;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Role;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.User;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Repository.AdminRepository;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Repository.RoleDao;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminDao;

    @Autowired
    private UserRepository userDao;


    public void initRoleAndUser() {
        // Create roles
        if (!roleDao.existsById("Admin")) {
            Role adminRole = new Role();
            adminRole.setRoleName("Admin");
            adminRole.setRoleDescription("Admin role");
            roleDao.save(adminRole);
        }
        if (!roleDao.existsById("User")) {
            Role userRole = new Role();
            userRole.setRoleName("User");
            userRole.setRoleDescription("User role");
            roleDao.save(userRole);
        }


    }

    public Admin registerAdmin(Admin admin) {
        Role role = roleDao.findById("Admin").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        admin.setRole(userRoles);
        admin.setPassword(getEncodedPassword(admin.getPassword()));

        adminDao.save(admin);
        return admin;
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
    public User registeruser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        Role role = roleDao.findById("User").orElseThrow(() -> new RuntimeException("Role not found"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);

        user.setPassword(getEncodedPassword(user.getPassword())); // Ensure password is valid
        userDao.save(user);
        return user;
    }

}
