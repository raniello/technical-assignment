package com.mdotm.assignment.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdotm.assignment.adapter.in.dto.PetResponse;
import com.mdotm.assignment.adapter.out.InMemoryPetRepository;
import com.mdotm.assignment.domain.Pet;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {
    private Pet existentPet = Pet.create("Fufi", "Dog", 3, "Mary");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryPetRepository petRepository;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        petRepository.clear();
        existentPet = petRepository.save(existentPet);
    }

    @Test
    void listAllPets() throws Exception {
        var result = mockMvc.perform(get("/api/pets/"))
                .andExpect(status().isOk())
                .andReturn();

        var json = result.getResponse().getContentAsString();

        var retrievedPets = mapper.readValue(json, new TypeReference<List<PetResponse>>() {
        });

        assertEquals(Arrays.asList(PetResponse.fromPet(existentPet)), retrievedPets);

    }

    @Test
    void getPetById() throws Exception {
        var result = mockMvc.perform(get("/api/pets/" + existentPet.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var json = result.getResponse().getContentAsString();
        var retrievedPet = mapper.readValue(json, PetResponse.class);

        assertEquals(PetResponse.fromPet(existentPet), retrievedPet);

    }

    @Test
    void getPetByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/pets/9999"))
                .andExpect(status().isNotFound());

    }
}
