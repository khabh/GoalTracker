package com.goaltracker.user.validator.annotation;


import com.goaltracker.user.validator.NoWhitespaceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoWhitespaceValidator.class)
public @interface NoWhitespace {

    String message() default "String must not contain whitespace";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
