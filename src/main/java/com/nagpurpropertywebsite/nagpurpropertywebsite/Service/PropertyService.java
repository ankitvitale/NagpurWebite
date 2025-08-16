package com.nagpurpropertywebsite.nagpurpropertywebsite.Service;

import com.nagpurpropertywebsite.nagpurpropertywebsite.DTO.PropertyDto;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Admin;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Enumration.Status;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Property;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.User;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Repository.AdminRepository;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Repository.PropertyRepository;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Repository.UserRepository;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Security.JwtAuthenticationFilter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Property createProperty(PropertyDto dto, List<MultipartFile> imageFiles) {
        String email = JwtAuthenticationFilter.CURRENT_USER;

        Optional<User> user = userRepository.findByEmail(email);
        Admin admin = adminRepository.findByEmail(email);

        if (user.isEmpty() && admin == null) {
            throw new RuntimeException("User not found");
        }

        Property property = new Property();
        property.setTitle(dto.getTitle());
        property.setDescription(dto.getDescription());
        property.setPropertyFor(dto.getPropertyFor());
        property.setPropertyType(dto.getPropertyType());
        property.setPrice(dto.getPrice());
        property.setArea(dto.getArea());
        property.setBedrooms(dto.getBedrooms());
        property.setBathrooms(dto.getBathrooms());
        property.setFurnishing(dto.getFurnishing());
        property.setAmenities(dto.getAmenities());
        property.setAddress(dto.getAddress());
        property.setLocality(dto.getLocality());
        property.setCity(dto.getCity());
        property.setState(dto.getState());
        property.setPincode(dto.getPincode());
        property.setStatus(Status.PENDING);
        property.setUser(user.orElse(null));  // Regular user, set user


        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            try {
                String imageUrl = imageService.uploadFileToSpace(file);
                imageUrls.add(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image: " + file.getOriginalFilename(), e);
            }
        }

        property.setImages(imageUrls);
        return propertyRepository.save(property);
    }

    public Property editProperty(Long propertyId, PropertyDto dto, List<MultipartFile> imageFiles) {
        String email = JwtAuthenticationFilter.CURRENT_USER;

        Optional<User> user = userRepository.findByEmail(email);
        Admin admin = adminRepository.findByEmail(email);

        if (user.isEmpty() && admin == null) {
            throw new RuntimeException("User not found");
        }

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));

        property.setTitle(dto.getTitle());
        property.setDescription(dto.getDescription());
        property.setPropertyFor(dto.getPropertyFor());
        property.setPropertyType(dto.getPropertyType());
        property.setPrice(dto.getPrice());
        property.setArea(dto.getArea());
        property.setBedrooms(dto.getBedrooms());
        property.setBathrooms(dto.getBathrooms());
        property.setFurnishing(dto.getFurnishing());
        property.setAmenities(dto.getAmenities());
        property.setAddress(dto.getAddress());
        property.setLocality(dto.getLocality());
        property.setCity(dto.getCity());
        property.setState(dto.getState());
        property.setPincode(dto.getPincode());
        property.setStatus(Status.PENDING);
        property.setUser(user.orElse(null));  // only if changed

        // Optional: Replace images if new ones are uploaded
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile file : imageFiles) {
                try {
                    String imageUrl = imageService.uploadFileToSpace(file);
                    imageUrls.add(imageUrl);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to upload image: " + file.getOriginalFilename(), e);
                }
            }
            property.setImages(imageUrls);
        }

        return propertyRepository.save(property);
    }


    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property createProperty1(PropertyDto dto, List<MultipartFile> images) {

        Property property = new Property();
        property.setTitle(dto.getTitle());
        property.setDescription(dto.getDescription());
        property.setPropertyFor(dto.getPropertyFor());
        property.setPropertyType(dto.getPropertyType());
        property.setPrice(dto.getPrice());
        property.setArea(dto.getArea());
        property.setBedrooms(dto.getBedrooms());
        property.setBathrooms(dto.getBathrooms());
        property.setFurnishing(dto.getFurnishing());
        property.setAmenities(dto.getAmenities());
        property.setAddress(dto.getAddress());
        property.setLocality(dto.getLocality());
        property.setCity(dto.getCity());
        property.setState(dto.getState());
        property.setPincode(dto.getPincode());
        property.setStatus(Status.PENDING);

        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : images) {
            try {
                String imageUrl = imageService.uploadFileToSpace(file);
                imageUrls.add(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image: " + file.getOriginalFilename(), e);
            }
        }

        property.setImages(imageUrls);
        return propertyRepository.save(property);
    }


    public List<Property> getPropertiesByCurrentUser(String email) {
        return propertyRepository.findByUserEmail(email);

    }

    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }


    public Property updateStatus(Long id, Status status) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));

        property.setStatus(status);
        return propertyRepository.save(property);
    }
}
