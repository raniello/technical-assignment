package com.mdotm.assignment.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mdotm.assignment.application.PetService;
import com.mdotm.assignment.domain.Pet;
import com.mdotm.assignment.domain.port.PetRepository;


@ExtendWith(MockitoExtension.class)
public class PetServiceTest {
    
    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Test
    void listAllPets(){
        var expectedPets =  Arrays.asList(
            Pet.create(1L, "Anita", "Cat", 10, "Robertino"),
            Pet.create(2L, "Fred", "Cat", 8, "Sara")
        );

        when(petRepository.findAll()).thenReturn(new ArrayList<>(expectedPets));

        var actualPets = petService.listAll();

        assertEquals(expectedPets, actualPets);
    }
}
