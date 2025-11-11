package com.mdotm.assignment.adapter.in.dto;

import com.mdotm.assignment.domain.Pet;

public record PetResponse(
        Long id,
        String name,
        String species,
        Integer age,
        String ownerName
) {

    public static PetResponse fromPet(Pet pet){
        return new PetResponse(pet.getId(), pet.getName(), pet.getSpecies(), pet.getAge(), pet.getOwnerName());
    }
}
