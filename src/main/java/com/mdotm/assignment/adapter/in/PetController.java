package com.mdotm.assignment.adapter.in;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mdotm.assignment.adapter.in.dto.CreatePetRequest;
import com.mdotm.assignment.adapter.in.dto.PatchPetRequest;
import com.mdotm.assignment.adapter.in.dto.PetResponse;
import com.mdotm.assignment.adapter.in.dto.UpdatePetRequest;
import com.mdotm.assignment.application.PetService;
import com.mdotm.assignment.application.dto.PetDataDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping({ "", "/" })
    public ResponseEntity<List<PetResponse>> listAll() {
        return ResponseEntity.ok(petService.listAll().stream().map(PetResponse::fromPet).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getById(@PathVariable Long id) {
        return petService.getById(id)
                .map(pet -> ResponseEntity.ok(PetResponse.fromPet(pet)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping({ "", "/" })
    public ResponseEntity<PetResponse> create(@Valid @RequestBody CreatePetRequest request) {
        var pet = petService
                .create(new PetDataDto(request.name(), request.species(), request.age(), request.ownerName()));
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pet.getId())
                .toUri();
        return ResponseEntity.created(location).body(PetResponse.fromPet(pet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> update(@PathVariable Long id, @Valid @RequestBody UpdatePetRequest request) {
        var pet = petService.update(id, new PetDataDto(request.name(), request.species(), request.age(), request.ownerName()));
        return ResponseEntity.ok(PetResponse.fromPet(pet));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PetResponse> patch(@PathVariable Long id, @Valid @RequestBody PatchPetRequest request) {
        var pet = petService.partiallyUpdate(id, new PetDataDto(request.name(), request.species(), request.age(), request.ownerName()));
        return ResponseEntity.ok(PetResponse.fromPet(pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PetResponse> delete(@PathVariable Long id) {
        var deleted = petService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}