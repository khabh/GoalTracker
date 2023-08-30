package com.goaltracker.checklist.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "checklist_states")
@Getter
@Setter
public class ChecklistState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "checklist_history_id")
    private ChecklistHistory checklistHistory;

    @ManyToOne
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;

    private boolean isCompleted = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChecklistState that = (ChecklistState) o;
        return isCompleted == that.isCompleted && Objects.equals(id, that.id) && Objects.equals(checklistHistory, that.checklistHistory) && Objects.equals(checklist, that.checklist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checklistHistory, checklist, isCompleted);
    }
}
