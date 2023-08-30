package com.goaltracker.checklist.validator.annotation;

import com.goaltracker.checklist.validator.ChecklistsNotBlankValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ChecklistsNotBlankValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChecklistsNotBlank {
    String message() default "Checklists contain only whitespace or are empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}