package com.goaltracker.checklist.schedule;

import com.goaltracker.checklist.service.ChecklistHistoryService;
import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DailyChecklistHistoryTask {

    private final static String EVERY_DAY = "0 0 0 * * *";
    private final ChecklistHistoryService checklistHistoryService;
    private final GoalService goalService;

    @Autowired
    public DailyChecklistHistoryTask(ChecklistHistoryService checklistHistoryService, GoalService goalService) {
        this.checklistHistoryService = checklistHistoryService;
        this.goalService = goalService;
    }

    @Scheduled(cron = EVERY_DAY)
    public void executeDailyChecklistHistoryTask() {
        List<Goal> activeGoals = goalService.getGoalsWithDueDateNotPassed();
        checklistHistoryService.addChecklistHistoryForGoals(activeGoals);
    }
}
