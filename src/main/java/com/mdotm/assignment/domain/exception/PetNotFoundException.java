package com.mdotm.assignment.domain.exception;

public class PetNotFoundException extends PetException {

    public PetNotFoundException(Long petId) {
        super("Pet with ID '%d' not found".formatted(petId));
    }
    
}
