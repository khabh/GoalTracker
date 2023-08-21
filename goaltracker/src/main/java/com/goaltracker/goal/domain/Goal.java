package com.goaltracker.goal.domain;

import com.goaltracker.checklist.domain.Checklist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "goals")
@Getter
@Setter
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDate dueDate;
    private float progress;

    @OneToMany(mappedBy = "goal")
    private List<Checklist> checklists;

    public List<Checklist> getActiveChecklists() {
        return checklists.stream()
                .filter(Checklist::isCurrentlyActive)
                .toList();
    }
}

