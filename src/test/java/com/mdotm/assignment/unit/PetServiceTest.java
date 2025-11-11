package com.mdotm.assignment.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mdotm.assignment.application.PetService;
import com.mdotm.assignment.application.dto.PetDataDto;
import com.mdotm.assignment.domain.Pet;
import com.mdotm.assignment.domain.exception.PetNotFoundException;
import com.mdotm.assignment.domain.port.PetRepository;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Test
    void listAllPets() {
        var expectedPets = Arrays.asList(
                Pet.withId(1L, "Anita", "Cat", 10, "Robertino"),
                Pet.withId(2L, "Fred", "Cat", 8, "Sara"));

        when(petRepository.findAll()).thenReturn(new ArrayList<>(expectedPets));

        var actualPets = petService.listAll();

        assertEquals(expectedPets, actualPets);
    }

    @Test
    void getPetById() {
        var expectedPet = Pet.withId(1L, "Anita", "Cat", 10, "Robertino");

        when(petRepository.getById(expectedPet.getId())).thenReturn(Optional.of(expectedPet));

        var actualPet = petService.getById(expectedPet.getId());

        assertEquals(expectedPet, actualPet.orElse(null));
    }

    @Test
    void getPetByIdNotFound() {
        var actualPet = petService.getById(1L);

        assertEquals(Optional.empty(), actualPet);
    }

    @Test
    void createPet() {
        var petData = new PetDataDto("Anita", "Cat", 10, "Robertino");
        var expectedSavedPet = petData.asPet(3L);

        when(petRepository.save(argThat(pet -> pet.getId() == null && petData.equals(PetDataDto.fromPet(pet)))))
                .thenReturn(expectedSavedPet);

        var actualSavedPet = petService.create(petData);

        assertEquals(expectedSavedPet, actualSavedPet);
    }

    @Test
    void updatePet() {
        var petId = Long.valueOf(1L);
        var existingPet = Pet.withId(petId, "Gred", "Dog", 12);
        var updateData = new PetDataDto("Anita", "Cat", 10, "Robertino");
        var expectedUpdatedPet = updateData.asPet(petId);

        when(petRepository.getById(petId)).thenReturn(Optional.of(existingPet));
        when(petRepository
                .save(argThat(pet -> petId.equals(pet.getId()) && updateData.equals(PetDataDto.fromPet(pet)))))
                .thenReturn(expectedUpdatedPet);

        var actualUpdatedPet = petService.update(petId, updateData);

        assertEquals(expectedUpdatedPet, actualUpdatedPet);
    }

    @Test
    void updatePetNotFound() {
        var petId = Long.valueOf(1L);
        var updateData = new PetDataDto("Anita", "Cat", 10, "Robertino");

        assertThrows(PetNotFoundException.class, () -> petService.update(petId, updateData));
    }

    @Test
    void partiallyUpdatePet(){
        var petId = Long.valueOf(1L);
        var existingPet = Pet.withId(petId, "Gred", "Dog", 12, "Jack");
        var updatedSpecies = "Cat";
        var updatedAge = 8;
        var expectedUpdatedPetData = new PetDataDto(
            existingPet.getName(),
            updatedSpecies,
            updatedAge,
            existingPet.getOwnerName()
        );

        when(petRepository.getById(petId)).thenReturn(Optional.of(existingPet));
        when(petRepository.save(argThat(pet -> petId.equals(pet.getId()))))
        .thenAnswer(invocation -> invocation.getArgument(0, Pet.class));

        var updateData = PetDataDto.empty().withSpecies(updatedSpecies).withAge(updatedAge);
        var actualUpdatedPet = petService.partiallyUpdate(petId, updateData);
        var actualUpdatedPedData = PetDataDto.fromPet(actualUpdatedPet);

        assertEquals(petId, actualUpdatedPet.getId());
        assertEquals(expectedUpdatedPetData, actualUpdatedPedData);
    }

    @Test 
    void partiallyUpdatePetNotFound(){
        var petId = Long.valueOf(1L);
        var updateData = PetDataDto.empty().withSpecies("Cat").withAge(8);
        
        assertThrows(PetNotFoundException.class, () -> petService.partiallyUpdate(petId, updateData));
    }
}
