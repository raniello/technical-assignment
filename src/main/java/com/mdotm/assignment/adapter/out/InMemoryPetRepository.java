package com.mdotm.assignment.adapter.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import com.mdotm.assignment.domain.Pet;
import com.mdotm.assignment.domain.port.PetRepository;

@Repository
public class InMemoryPetRepository implements PetRepository {

    private final ConcurrentMap<Long, Pet> pets = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public List<Pet> findAll() {
        return new ArrayList<>(pets.values());
    }

    @Override
    public Optional<Pet> getById(Long id) {
        return Optional.ofNullable(pets.get(id));
    }

    @Override
    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet = pet.withId(idCounter.incrementAndGet());
        }
        pets.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public void deleteById(Long petId) {
        pets.remove(petId);
    }

    public void clear(){
        pets.clear();
    }
}
