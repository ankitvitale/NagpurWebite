package com.nagpurpropertywebsite.nagpurpropertywebsite.Controller;

import com.nagpurpropertywebsite.nagpurpropertywebsite.DTO.PropertyEnquireDto;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Enquire;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Service.EnquireService;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Service.PropertyEnquireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EnquireController {
    @Autowired
    private EnquireService enquireService;

    @Autowired
    private PropertyEnquireService propertyEnquireService;
        @PostMapping("/Enquire")
        public ResponseEntity<Enquire> createEnquire(@RequestBody Enquire enquire){
            return enquireService.createEnquire(enquire);
        }

        @GetMapping("/show-Enquire")
        public ResponseEntity<List<Enquire>> getAllEnquier(){
            List<Enquire> allEnquire = enquireService.AllEnqurie();
            return ResponseEntity.ok(allEnquire);
        }
         @GetMapping("/show-Enquire/{id}")
         @PreAuthorize("hasRole('Admin')")
         public ResponseEntity<Enquire> getEnquierById(Long id){
            Enquire enquire=enquireService.getEnquierById(id);
            return ResponseEntity.ok(enquire);

        }

          @DeleteMapping("/delete-Enquire/{id}")
          @PreAuthorize("hasRole('Admin')")
          public ResponseEntity<String> deleteEnquier(@PathVariable  Long id){
             enquireService.deleteEnquire(id);
             return ResponseEntity.ok("Enquire Deleted Sucessfully...!!!");
        }

         @PostMapping("/Property-enquiries")
         public ResponseEntity<String> createPropertyEnquiry(@RequestBody PropertyEnquireDto dto) {
            propertyEnquireService.saveEnquiry(dto);
           return ResponseEntity.ok("Enquiry saved successfully");
    }
        @GetMapping("/AllProperty-enquiries")
        @PreAuthorize("hasAnyRole('Admin', 'User')")
        public ResponseEntity<List<PropertyEnquireDto>> getAllEnquiries() {
        List<PropertyEnquireDto> enquiries = propertyEnquireService.getAllEnquiries();
        return ResponseEntity.ok(enquiries);
    }
        @DeleteMapping("/deletePropertyEnquire/{id}")
        @PreAuthorize("hasRole('Admin')")
        public ResponseEntity<String> deletePropertyEnquire(@PathVariable Long id){
            propertyEnquireService.deletePropertyEnquire(id);
            return ResponseEntity.ok("Delete Property Successfully...!!");
   }

}
