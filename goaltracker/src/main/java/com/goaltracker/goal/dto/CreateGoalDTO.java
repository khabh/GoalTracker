package com.goaltracker.goal.dto;

import com.goaltracker.goal.validator.annotation.DueDateAfterToday;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateGoalDTO {
    @NotBlank(message = "Goal name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Due date is required")
    @DueDateAfterToday
    private LocalDate dueDate;

    @Override
    public String toString() {
        return "CreateGoalDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
