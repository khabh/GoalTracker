package com.goaltracker.checklist.dto;

import com.goaltracker.checklist.validator.annotation.ChecklistsNotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateChecklistsDTO {
    @NotNull(message = "Checklists are required")
    @ChecklistsNotBlank()
    private List<String> checklists;

    @Override
    public String toString() {
        return "CreateChecklistsDTO{" +
                "checklists=" + checklists +
                '}';
    }
}
