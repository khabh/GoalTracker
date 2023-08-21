package com.goaltracker.checklist.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "checklist_histories")
@Getter
@Setter
public class ChecklistHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @OneToMany(mappedBy = "checklistHistory", cascade = CascadeType.ALL)
    private List<ChecklistState> checklistStates = new ArrayList<>();

    public static ChecklistHistory from(LocalDate date, List<Checklist> checklists) {
        ChecklistHistory history = new ChecklistHistory();
        history.setDate(date);

        for (Checklist checklist : checklists) {
            ChecklistState state = new ChecklistState();
            state.setChecklist(checklist);
            state.setChecklistHistory(history);
            history.getChecklistStates().add(state);
        }

        return history;
    }

    public static ChecklistHistory from(List<Checklist> checklists) {
        return ChecklistHistory.from(LocalDate.now(), checklists);
    }
}
