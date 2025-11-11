package com.mdotm.assignment.adapter.in.dto;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdatePetRequest(
        @NotBlank String name,
        @NotBlank String species,
        @NotNull @PositiveOrZero Integer age,
        @Nullable String ownerName) {
}
