package dev.matheuscruz.resources;

import dev.matheuscruz.validators.CPFValido;

public record StudentDTO (
        String name,
        int age,
        @CPFValido
        String documentoCpf,
        String documentoCnpj,
        String email,
        String mensalidade

) {
}
