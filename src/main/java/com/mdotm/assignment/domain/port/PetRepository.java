package com.mdotm.assignment.domain.port;

import java.util.List;
import java.util.Optional;

import com.mdotm.assignment.domain.Pet;

public interface PetRepository {
    public List<Pet> findAll();

    public Optional<Pet> getById(Long id);

}