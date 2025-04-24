package com.nagpurpropertywebsite.nagpurpropertywebsite.Controller;

import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.JwtRequest;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.JwtResponse;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.User;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Service.AuthService;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;


    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }
    @PostMapping({"/registerUser"})
    public User registerNewAdmin(@RequestBody User user) {
        return userService.registeruser(user);
    }

    @PostMapping("/auth/login")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return authService.createJwtToken(jwtRequest);
    }
}
