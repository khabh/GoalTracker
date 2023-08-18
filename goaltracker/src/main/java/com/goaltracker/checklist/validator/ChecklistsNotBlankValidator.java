package com.goaltracker.checklist.validator;

import com.goaltracker.checklist.validator.annotation.ChecklistsNotBlank;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
public class ChecklistsNotBlankValidator implements ConstraintValidator<ChecklistsNotBlank, List<String>> {
    @Override
    public void initialize(ChecklistsNotBlank constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> checklists, ConstraintValidatorContext context) {
        if (checklists == null)
            return false;

        List<String> nonEmptyChecklists = checklists.stream()
                .filter(checklist -> checklist != null && !checklist.trim().isEmpty())
                .toList();

        return !nonEmptyChecklists.isEmpty();
    }
}