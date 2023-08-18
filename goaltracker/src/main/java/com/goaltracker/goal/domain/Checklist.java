package com.goaltracker.goal.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "checklists")
@Getter
@Setter
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private boolean completion = false;

    @ManyToOne()
    @JoinColumn(name = "goal_id")
    private Goal goal;

    public static Checklist from(String content, Goal goal) {
        Checklist checklist = new Checklist();
        checklist.setContent(content);
        checklist.setGoal(goal);

        return checklist;
    }
}
