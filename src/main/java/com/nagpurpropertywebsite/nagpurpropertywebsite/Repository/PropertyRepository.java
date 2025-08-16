package com.nagpurpropertywebsite.nagpurpropertywebsite.Repository;

import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property,Long> {

    List<Property> findByUserEmail(String email);
}
