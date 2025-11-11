package com.mdotm.assignment.application;

import java.util.List;
import java.util.Optional;

import com.mdotm.assignment.application.dto.PetDataDto;
import com.mdotm.assignment.domain.Pet;
import com.mdotm.assignment.domain.exception.PetNotFoundException;
import com.mdotm.assignment.domain.port.PetRepository;

public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> listAll() {
        return petRepository.findAll();
    }

    public Optional<Pet> getById(Long id) {
        return petRepository.getById(id);
    }

    public Pet create(PetDataDto petData) {
        return petRepository.save(petData.asPet());
    }

    public Pet update(long petId, PetDataDto petData) {
        var pet = petRepository.getById(petId).orElseThrow(() -> new PetNotFoundException(petId));
        pet.rename(petData.name());
        pet.changeSpecies(petData.species());
        pet.updateAge(petData.age());
        pet.updateOwnerName(petData.ownerName());
        return petRepository.save(pet);
    }

    public Pet partiallyUpdate(long petId, PetDataDto petData) {
        var pet = petRepository.getById(petId).orElseThrow(() -> new PetNotFoundException(petId));
        if (petData.name() != null) {
            pet.rename(petData.name());
        }
        if (petData.species() != null) {
            pet.changeSpecies(petData.species());
        }
        if (petData.age() != null) {
            pet.updateAge(petData.age());
        }
        if (petData.ownerName() != null) {
            pet.updateOwnerName(petData.ownerName());
        }
        return petRepository.save(pet);
    }

}
