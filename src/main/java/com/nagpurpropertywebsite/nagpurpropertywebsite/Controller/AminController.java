package com.nagpurpropertywebsite.nagpurpropertywebsite.Controller;

import com.nagpurpropertywebsite.nagpurpropertywebsite.DTO.PropertyDto;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Admin;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Property;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Service.PropertyService;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PropertyService propertyService;
    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }
    @PostMapping({"/registerAdmin"})
    public Admin registerNewAdmin(@RequestBody Admin admin) {
        return userService.registerAdmin(admin);
    }
    @PostMapping(value = "/addPropertybyAdmin",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Property> createProperty(
            @RequestPart("property") PropertyDto propertyDTO,
            @RequestPart("images") List<MultipartFile> images
    ) {
        Property saved = propertyService.createProperty1(propertyDTO, images);
        return ResponseEntity.ok(saved);
    }
}
