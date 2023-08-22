package com.goaltracker.checklist.util;

import com.goaltracker.checklist.domain.ChecklistHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ChecklistHistoryConverter {

    private ChecklistHistoryConverter() {}

    public static ChecklistHistory from(LocalDate localDate) {
        ChecklistHistory checklistHistory = new ChecklistHistory();
        checklistHistory.setDate(localDate);

        return checklistHistory;
    }

    public static List<ChecklistHistory> toChecklistHistories(LocalDate localDate, int size) {
        List<ChecklistHistory> checklistHistories = new ArrayList<>();
        IntStream.range(0, size)
                .forEach(index -> checklistHistories.add(ChecklistHistoryConverter.from(localDate)));

        return checklistHistories;
    }
}
