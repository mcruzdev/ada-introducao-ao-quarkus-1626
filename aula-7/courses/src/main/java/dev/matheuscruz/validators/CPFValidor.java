package dev.matheuscruz.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidor implements ConstraintValidator<CPFValido, String> {
    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isEmpty()){
            return false;
        }

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se tem 11 dígitos ou se é uma sequência repetida (invalida)
        if (cpf.length() != 11 ||
                cpf.equals("00000000000") || cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999")) {
            return false;
        }

        try {
            // Cálculo do primeiro dígito
            int soma = 0;
            int peso = 10;
            for (int i = 0; i < 9; i++) {
                soma += Integer.parseInt(cpf.substring(i, i + 1)) * peso--;
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) digito1 = 0;

            // Cálculo do segundo dígito
            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                soma += Integer.parseInt(cpf.substring(i, i + 1)) * peso--;
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) digito2 = 0;

            // Verifica se os dígitos calculados conferem com os informados
            return digito1 == Integer.parseInt(cpf.substring(9, 10)) &&
                    digito2 == Integer.parseInt(cpf.substring(10, 11));

        } catch (NumberFormatException e) {
            return false;
        }
    }

//        if (cpf == null || cpf.isEmpty()){
//            return false;
//        }
//
//        String digitosNumericosRegex = "\\d{11}";
//
//        return cpf.matches(digitosNumericosRegex);
//    }
}
