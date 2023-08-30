package com.goaltracker.checklist.util;

import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.domain.ChecklistState;

import java.util.List;
import java.util.stream.Collectors;

public class ChecklistStateConverter {

    private ChecklistStateConverter() {}

    public static ChecklistState from(Checklist checklist, ChecklistHistory checklistHistory) {
        ChecklistState checklistState = new ChecklistState();
        checklistState.setChecklist(checklist);
        checklistState.setChecklistHistory(checklistHistory);

        return checklistState;
    }

    public static List<ChecklistState> toChecklistStates(List<Checklist> checklists, ChecklistHistory checklistHistory) {
        return checklists.stream()
                .map(checklist -> ChecklistStateConverter.from(checklist, checklistHistory))
                .collect(Collectors.toList());
    }
}
