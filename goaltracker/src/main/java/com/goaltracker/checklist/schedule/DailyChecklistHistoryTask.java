package com.goaltracker.checklist.schedule;

import com.goaltracker.checklist.service.ChecklistHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyChecklistHistoryTask {

    private final static String EVERY_DAY = "0 0 0 * * *";
    private final ChecklistHistoryService checklistHistoryService;


    @Autowired
    public DailyChecklistHistoryTask(ChecklistHistoryService checklistHistoryService) {
        this.checklistHistoryService = checklistHistoryService;
    }

    @Scheduled(cron = EVERY_DAY)
    public void executeDailyChecklistHistoryTask() {
        checklistHistoryService.addChecklistHistoryForAllGoals();
    }
}
