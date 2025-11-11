package com.mdotm.assignment.application.dto;

import com.mdotm.assignment.domain.Pet;

public record PetDataDto(String name, String species, Integer age, String ownerName) {
    
    public PetDataDto withName(String name){
        return new PetDataDto(name, species, age, ownerName);
    }
    public PetDataDto withAge(Integer age){
        return new PetDataDto(name, species, age, ownerName);
    }
    public PetDataDto withSpecies(String species){
        return new PetDataDto(name, species, age, ownerName);
    }
    public PetDataDto withOwnerName(String ownerName){
        return new PetDataDto(name, species, age, ownerName);
    }

    public Pet asPet() {
        return Pet.withId(null, name(), species(), age(), ownerName());
    }

    public Pet asPet(Long petId) {
        return Pet.withId(petId, name(), species(), age(), ownerName());
    }

    public static PetDataDto fromPet(Pet pet) {
        return new PetDataDto(pet.getName(), pet.getSpecies(), pet.getAge(), pet.getOwnerName());
    }

    public static PetDataDto empty(){
        return new PetDataDto(null, null, null, null);
    }

}