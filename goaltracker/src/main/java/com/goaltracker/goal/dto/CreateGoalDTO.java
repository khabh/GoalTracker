package com.goaltracker.goal.dto;

import com.goaltracker.goal.domain.Checklist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreateGoalDTO {
    private String name;

    private String description;

    private LocalDate dueDate;

    private List<String> checklists;

    public List<Checklist> getConvertedChecklists() {
        return checklists.stream()
                .filter(checklist -> checklist != null && !checklist.trim().isEmpty())
                .map(Checklist::fromContent)
                .toList();
    }

    @Override
    public String toString() {
        return "CreateGoalDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", checklists=" + checklists +
                '}';
    }
}
