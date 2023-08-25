package com.goaltracker;

import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.domain.ChecklistState;
import com.goaltracker.checklist.repository.ChecklistHistoryRepository;
import com.goaltracker.checklist.repository.ChecklistRepository;
import com.goaltracker.checklist.repository.ChecklistStateRepository;
import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class TestUtil {

    @Autowired
    ChecklistHistoryRepository checklistHistoryRepository;

    @Autowired
    ChecklistRepository checklistRepository;

    @Autowired
    ChecklistStateRepository checklistStateRepository;

    @Autowired
    GoalRepository goalRepository;

    public ChecklistHistory createChecklistHistory(LocalDate date) {
        ChecklistHistory checklistHistory = new ChecklistHistory();
        checklistHistory.setDate(date);
        return checklistHistoryRepository.save(checklistHistory);
    }

    public Goal createGoal(LocalDate dueDate) {
        Goal goal = new Goal();
        goal.setDueDate(dueDate);
        goal.setName("goalName");
        goal.setDescription("goal description");
        return goalRepository.save(goal);
    }

    public Goal createGoalWithChecklists(LocalDate dueDate, int checklistCount) {
        Goal goal = createGoal(dueDate);
        goal.setChecklists(createChecklists(goal, checklistCount));

        return goal;
    }

    public List<Checklist> createChecklists(Goal goal, int count) {
        return IntStream.range(0, count)
                .mapToObj(index -> {
                    Checklist checklist = new Checklist();
                    checklist.setGoal(goal);
                    checklist.setContent("content" + index);
                    return checklist;
                })
                .map(checklistRepository::save)
                .collect(Collectors.toList());
    }

    public List<ChecklistState> createChecklistStates(List<Checklist> checklists, ChecklistHistory checklistHistory) {
        return checklists.stream()
                .map(checklist -> {
                    ChecklistState checklistState = new ChecklistState();
                    checklistState.setChecklist(checklist);
                    checklistState.setChecklistHistory(checklistHistory);
                    return checklistStateRepository.save(checklistState);
                })
                .collect(Collectors.toList());
    }

    public void addCompletedChecklistStates(Checklist checklist, ChecklistHistory checklistHistory, int count) {
        IntStream.range(0, count)
                .forEach(index -> {
                    ChecklistState checklistState = new ChecklistState();
                    checklistState.setChecklist(checklist);
                    checklistState.setChecklistHistory(checklistHistory);
                    checklistState.setCompleted(true);
                    checklistStateRepository.save(checklistState);
                });
    }
}
