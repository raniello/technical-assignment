package com.mdotm.assignment.domain.port;

import java.util.List;
import com.mdotm.assignment.domain.Pet;

public interface PetRepository {
    public List<Pet> findAll();
}