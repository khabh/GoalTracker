package com.goaltracker.goal.validator;

import com.goaltracker.goal.validator.annotation.DueDateAfterToday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DueDateAfterTodayValidator implements ConstraintValidator<DueDateAfterToday, LocalDate> {
    @Override
    public void initialize(DueDateAfterToday constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate dueDate, ConstraintValidatorContext context) {
        if (dueDate == null)
            return false;

        System.out.println("dueDate = " + dueDate);
        return dueDate.isAfter(LocalDate.now());
    }
}