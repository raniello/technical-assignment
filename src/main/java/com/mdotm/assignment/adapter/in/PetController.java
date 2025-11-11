package com.mdotm.assignment.adapter.in;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdotm.assignment.adapter.in.dto.PetResponse;
import com.mdotm.assignment.application.PetService;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<PetResponse>> listAll() {
        return ResponseEntity.ok(petService.listAll().stream().map(PetResponse::fromPet).toList());
    }
}