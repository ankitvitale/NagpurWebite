package com.nagpurpropertywebsite.nagpurpropertywebsite.Controller;

import com.nagpurpropertywebsite.nagpurpropertywebsite.DTO.PropertyDto;
import com.nagpurpropertywebsite.nagpurpropertywebsite.DTO.PropertyStatusUpdateDto;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Property;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Service.PropertyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PropertyController {
    @Autowired
  private PropertyService propertyService;

    @PostMapping(value = "/addProperty",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<Property> createProperty(
            @RequestPart("property") PropertyDto propertyDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        Property saved = propertyService.createProperty(propertyDTO, images);
        return ResponseEntity.ok(saved);
    }


    @PutMapping(value = "/editProperty/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<Property> editProperty(
            @PathVariable Long propertyId,
            @RequestPart("property") PropertyDto propertyDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        Property updated = propertyService.editProperty(propertyId, propertyDTO, images);
        return ResponseEntity.ok(updated);
    }


    @GetMapping("/properties")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<List<Property>> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/userproperties")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<List<Property>> getPropertiesForCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        List<Property> properties = propertyService.getPropertiesByCurrentUser(email);
        return ResponseEntity.ok(properties);
    }
    @GetMapping("/property/{id}")
   // @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<Optional<Property>> getPropertyById(@PathVariable Long id){
        Optional<Property> property=propertyService.getPropertyById(id);
        return ResponseEntity.ok(property);
    }

    @PatchMapping("/updateStatus/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'User')")
    public ResponseEntity<?> updatePropertyStatus(@PathVariable Long id,
                                                  @RequestBody PropertyStatusUpdateDto statusDto) {
        try {
            Property updated = propertyService.updateStatus(id, statusDto.getStatus());
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }



}
