package com.mdotm.assignment.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdotm.assignment.adapter.out.InMemoryPetRepository;
import com.mdotm.assignment.domain.Pet;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.mdotm.assignment.adapter.in.dto.*;

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

    @Test
    void createPet() throws Exception {
        var createPetRequest = new CreatePetRequest("Lemon", "Cat", 2, "Larry");
        var result = mockMvc.perform(post("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createPetRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var json = result.getResponse().getContentAsString();
        var savedPet = mapper.readValue(json, PetResponse.class);

        var expectedPetResponse = new PetResponse(
                savedPet.id(),
                createPetRequest.name(),
                createPetRequest.species(),
                createPetRequest.age(),
                createPetRequest.ownerName());
        assertEquals(expectedPetResponse, savedPet);
    }

    @Test
    void createPetBadRequest() throws Exception {
        var createPetRequest = new CreatePetRequest("Lemon", "Cat", -2, "Larry");
        mockMvc.perform(post("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createPetRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePet() throws Exception {
        var updatePetRequest = new UpdatePetRequest("Lemon", "Cat", 2, "Larry");
        var result = mockMvc.perform(put("/api/pets/" + existentPet.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatePetRequest)))
                .andExpect(status().isOk())
                .andReturn();

        var json = result.getResponse().getContentAsString();
        var updatedPet = mapper.readValue(json, PetResponse.class);

        var expectedPetResponse = new PetResponse(
                existentPet.getId(),
                updatePetRequest.name(),
                updatePetRequest.species(),
                updatePetRequest.age(),
                updatePetRequest.ownerName());
        assertEquals(expectedPetResponse, updatedPet);
    }

    @Test
    void updatePetNotFound() throws Exception {
        var updatePetRequest = new UpdatePetRequest("Lemon", "Cat", 2, "Larry");
        mockMvc.perform(put("/api/pets/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatePetRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void partiallyUpdatePet()  throws Exception {
        var patchPetRequest = new PatchPetRequest(null, "Cat", 2, null);
        var result = mockMvc.perform(patch("/api/pets/"+existentPet.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(patchPetRequest)))
                .andExpect(status().isOk())
                .andReturn();

        var json = result.getResponse().getContentAsString();
        var updatedPet = mapper.readValue(json, PetResponse.class);

        
        var expectedPetResponse = new PetResponse(
            existentPet.getId(), 
            existentPet.getName(),
            patchPetRequest.species(),
            patchPetRequest.age(),
            existentPet.getOwnerName()
        );
        assertEquals(expectedPetResponse, updatedPet);
    }

    @Test
    void partiallyUpdatePetNotFound()  throws Exception {
        var updatePetRequest = new UpdatePetRequest("Lemon", "Cat", 2, "Larry");
        mockMvc.perform(patch("/api/pets/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatePetRequest)))
                .andExpect(status().isNotFound());
    }

}
