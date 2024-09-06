package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UsuarioRecordDto(@NotNull long cpf, @NotBlank String nome, @NotNull LocalDate data_nascimento) {
}
