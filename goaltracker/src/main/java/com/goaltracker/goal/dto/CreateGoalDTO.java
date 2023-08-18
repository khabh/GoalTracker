package com.goaltracker.goal.dto;

import com.goaltracker.goal.domain.Checklist;
import com.goaltracker.goal.domain.Goal;
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

    public List<Checklist> getChecklists(Goal goal) {
        return checklists.stream()
                .filter(checklist -> checklist != null && !checklist.trim().isEmpty())
                .map(content -> Checklist.from(content, goal))
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
