package com.nagpurpropertywebsite.nagpurpropertywebsite.Service;

import com.nagpurpropertywebsite.nagpurpropertywebsite.DTO.PropertyEnquireDto;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Enquire;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Property;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.PropertyEnquire;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Repository.PropertyEnquireRepository;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyEnquireService {
    @Autowired
    private PropertyEnquireRepository propertyEnquireRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    public void saveEnquiry(PropertyEnquireDto dto) {
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        PropertyEnquire enquiry = new PropertyEnquire();
        enquiry.setFullName(dto.getFullName());
        enquiry.setEmail(dto.getEmail());
        enquiry.setPhone(dto.getPhone());
        enquiry.setMessage(dto.getMessage());
        enquiry.setVisitDate(dto.getVisitDate());
        enquiry.setProperty(property);

        propertyEnquireRepository.save(enquiry);
    }

    public List<PropertyEnquireDto> getAllEnquiries() {
        List<PropertyEnquire> enquiries = propertyEnquireRepository.findAll();
        return enquiries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PropertyEnquireDto convertToDto(PropertyEnquire enquiry) {
        PropertyEnquireDto dto = new PropertyEnquireDto();
        dto.setFullName(enquiry.getFullName());
        dto.setEmail(enquiry.getEmail());
        dto.setPhone(enquiry.getPhone());
        dto.setMessage(enquiry.getMessage());
        dto.setVisitDate(enquiry.getVisitDate());
        dto.setPropertyId(enquiry.getProperty().getId());

        return dto;
    }

    public void deletePropertyEnquire(Long id) {
        PropertyEnquire propertyEnquire=propertyEnquireRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("ID is not found "+id));
        propertyEnquireRepository.deleteById(id);
    }
}
