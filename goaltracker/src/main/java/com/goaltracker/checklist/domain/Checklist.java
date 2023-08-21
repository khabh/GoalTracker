package com.goaltracker.checklist.domain;

import com.goaltracker.goal.domain.Goal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    public static Checklist from(String content, Goal goal) {
        Checklist checklist = new Checklist();
        checklist.setContent(content);
        checklist.setGoal(goal);

        return checklist;
    }
}
