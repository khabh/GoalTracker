package com.goaltracker.goal.validator.annotation;

import com.goaltracker.goal.validator.DueDateAfterTodayValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DueDateAfterTodayValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DueDateAfterToday {
    String message() default "Due date is not after today or empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}