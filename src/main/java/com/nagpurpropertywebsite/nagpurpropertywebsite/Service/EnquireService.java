package com.nagpurpropertywebsite.nagpurpropertywebsite.Service;

import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Enquire;
import com.nagpurpropertywebsite.nagpurpropertywebsite.Repository.EnquireRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnquireService {
    @Autowired
    private EnquireRepository enquireRepository;
    public ResponseEntity<Enquire> createEnquire(Enquire enquire) {
        Enquire saveEnquery = enquireRepository.save(enquire);
        return ResponseEntity.ok(saveEnquery);
    }

    public List<Enquire> AllEnqurie() {
        return enquireRepository.findAll();
    }


    public void deleteEnquire(Long id) {
        Enquire enquire=enquireRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("ID is not found "+id));
        enquireRepository.deleteById(id);
    }

    public Enquire getEnquierById(Long id) {
        return enquireRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("ID is not Found"));

    }
}
