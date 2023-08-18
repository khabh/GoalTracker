package com.goaltracker.goal.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateGoalDTO {
    private String name;

    private String description;

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
