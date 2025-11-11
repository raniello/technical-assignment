package com.mdotm.assignment.domain;

public class Pet {
    private final Long id;
    private String name;
    private String species;
    private Integer age;
    private String ownerName;
    
    private Pet(Long id, String name, String species, Integer age, String ownerName) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.age = age;
        this.ownerName = ownerName;
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

    public static Pet create(Long petId, String name, String species, Integer age) {
        return create(petId, name, species, age, null);
    }

    public static Pet create(Long petId, String name, String species, Integer age, String ownerName) {
        return new Pet(petId, name, species, age, ownerName);
    }
}
