package com.luiz.first_dynamo_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ProductDto(
        UUID id,
        @NotBlank String name,
        @NotNull @Positive Double price) {
}