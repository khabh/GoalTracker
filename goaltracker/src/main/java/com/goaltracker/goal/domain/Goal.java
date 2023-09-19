package com.goaltracker.goal.domain;

import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

    @ManyToOne
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return Float.compare(goal.progress, progress) == 0 && Objects.equals(id, goal.id) && Objects.equals(name, goal.name) && Objects.equals(description, goal.description) && Objects.equals(dueDate, goal.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, dueDate, progress);
    }
}

