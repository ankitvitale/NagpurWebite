package com.nagpurpropertywebsite.nagpurpropertywebsite.DTO;

import com.nagpurpropertywebsite.nagpurpropertywebsite.Entity.Enumration.Status;

public class PropertyStatusUpdateDto {
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
