package dev.matheuscruz.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Constraint(validatedBy = CPFValidor.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFValido {

    String message() default "Esse CPF é invalido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
