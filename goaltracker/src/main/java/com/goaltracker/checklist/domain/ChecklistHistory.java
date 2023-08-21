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
}
