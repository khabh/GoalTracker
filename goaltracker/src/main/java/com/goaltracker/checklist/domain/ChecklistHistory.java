package com.goaltracker.checklist.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChecklistHistory that = (ChecklistHistory) o;
        return Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(checklistStates, that.checklistStates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, checklistStates);
    }
}
