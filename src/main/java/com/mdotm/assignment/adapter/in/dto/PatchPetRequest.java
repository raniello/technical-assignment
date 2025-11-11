package com.mdotm.assignment.adapter.in.dto;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.PositiveOrZero;

public record PatchPetRequest(
        @Nullable String name,
        @Nullable String species,
        @Nullable @PositiveOrZero Integer age,
        @Nullable String ownerName) {
}
