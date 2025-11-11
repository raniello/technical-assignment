package com.mdotm.assignment.application;

import java.util.List;

import com.mdotm.assignment.domain.Pet;
import com.mdotm.assignment.domain.port.PetRepository;

public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> listAll() {
        return petRepository.findAll();
    }
}
