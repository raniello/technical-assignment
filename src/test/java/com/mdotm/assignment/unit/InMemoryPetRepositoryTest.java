package com.mdotm.assignment.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.mdotm.assignment.adapter.out.InMemoryPetRepository;
import com.mdotm.assignment.application.dto.PetDataDto;
import com.mdotm.assignment.domain.Pet;

public class InMemoryPetRepositoryTest {

    @Test
    public void FindAllEmptyRepository() {
        var petRepository = new InMemoryPetRepository();

        assertEquals(Collections.emptyList(), petRepository.findAll());
    }

    @Test
    public void GetByIdNotFound() {
        var petRepository = new InMemoryPetRepository();

        assertEquals(Optional.empty(), petRepository.getById(1L));
    }

    @Test
    public void SaveNewAndRetrieve() {
        var petRepository = new InMemoryPetRepository();

        var pet = Pet.create("Fred", "Cat", 10);
        var saved = petRepository.save(pet);

        assertNotNull(saved.getId());
        assertEquals(PetDataDto.fromPet(pet), PetDataDto.fromPet(saved));

        var retrieved = petRepository.getById(saved.getId());
        assertEquals(saved, retrieved.get());

        assertEquals(Arrays.asList(saved), petRepository.findAll());

    }

    @Test
    public void UpdateAndRetrieve() {
        var petRepository = new InMemoryPetRepository();

        var pet = Pet.withId(1L, "Fred", "Cat", 10);
        var saved = petRepository.save(pet);

        assertEquals(pet.getId(), saved.getId());
        assertEquals(com.mdotm.assignment.application.dto.PetDataDto.fromPet(pet), PetDataDto.fromPet(saved));

        var retrieved = petRepository.getById(saved.getId());
        assertEquals(saved, retrieved.get());

        assertEquals(Arrays.asList(saved), petRepository.findAll());

    }

    @Test
    public void Delete() {
        var petRepository = new InMemoryPetRepository();

        var pet = Pet.create("Fred", "Cat", 10);
        var savedId = petRepository.save(pet).getId();

        petRepository.deleteById(savedId);

        assertEquals(Collections.emptyList(), petRepository.findAll());
    }

}
