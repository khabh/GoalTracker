package com.goaltracker.checklist.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}
