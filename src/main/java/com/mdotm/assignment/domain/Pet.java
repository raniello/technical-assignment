package com.mdotm.assignment.domain;

import com.mdotm.assignment.domain.exception.PetValidationException;

public class Pet {
    private final Long id;
    private String name;
    private String species;
    private Integer age;
    private String ownerName;

    private Pet(Long id, String name, String species, Integer age, String ownerName) {
        this.id = id;
        rename(name);
        changeSpecies(species);
        updateAge(age);
        updateOwnerName(ownerName);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public Integer getAge() {
        return age;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void rename(String name) {
        validateName(name);
        this.name = name;
    }

    public void changeSpecies(String species) {
        validateSpecies(species);
        this.species = species;
    }

    public void updateAge(Integer age) {
        validateAge(age);
        this.age = age;
    }

    public void updateOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Pet withId(long id) {
        return new Pet(id, name, species, age, ownerName);
    }
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new PetValidationException("Name is required");
        }
    }

    private void validateSpecies(String species) {
        if (species == null || species.isBlank()) {
            throw new PetValidationException("Species is required");
        }
    }

    private void validateAge(Integer age) {
        if (age == null || age < 0) {
            throw new PetValidationException("Age is required");
        }
    }

    public static Pet create(String name, String species, Integer age) {
        return create(name, species, age, null);
    }

    public static Pet create(String name, String species, Integer age, String ownerName) {
        return new Pet(null, name, species, age, ownerName);
    }

    public static Pet withId(Long petId, String name, String species, Integer age) {
        return withId(petId, name, species, age, null);
    }

    public static Pet withId(Long petId, String name, String species, Integer age, String ownerName) {
        return new Pet(petId, name, species, age, ownerName);
    }
}
