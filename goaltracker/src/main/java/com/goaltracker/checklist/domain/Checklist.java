package com.goaltracker.checklist.domain;

import com.goaltracker.goal.domain.Goal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "checklists")
@Getter
@Setter
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private boolean isDeletedFromDaily = false;

    @ManyToOne()
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @OneToMany(mappedBy = "checklist")
    private List<ChecklistState> checklistStates;

    public boolean isCurrentlyActive() {
        return !isDeletedFromDaily;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checklist checklist = (Checklist) o;
        return isDeletedFromDaily == checklist.isDeletedFromDaily && Objects.equals(id, checklist.id) && Objects.equals(content, checklist.content) && Objects.equals(goal, checklist.goal) && Objects.equals(checklistStates, checklist.checklistStates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, isDeletedFromDaily, goal, checklistStates);
    }
}
