package com.goaltracker.checklist.service;

import com.goaltracker.TestUtil;
import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.dto.ChecklistHistoryContentStateDTO;
import com.goaltracker.checklist.dto.ChecklistHistoryViewDTO;
import com.goaltracker.checklist.repository.ChecklistHistoryRepository;
import com.goaltracker.goal.domain.Goal;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class ChecklistHistoryServiceImplTest {

    @Autowired
    TestUtil testUtil;

    @Mock
    ChecklistStateService checklistStateService;

    @Mock
    ChecklistHistoryRepository checklistHistoryRepository;

    @InjectMocks
    ChecklistHistoryServiceImpl checklistHistoryService;

    @Test
    void testAddChecklistHistoryForGoals() {
        // Given
        int numberOfGoals = 3;
        LocalDate today = LocalDate.now();
        List<Goal> goals = IntStream.range(0, numberOfGoals)
                .mapToObj(index -> testUtil.createGoal(today))
                .collect(Collectors.toList());
        List<ChecklistHistory> expectedChecklistHistories = IntStream.range(0, numberOfGoals)
                .mapToObj(index -> getChecklistHistory(today))
                .collect(Collectors.toList());

        // When
        checklistHistoryService.addChecklistHistoryForGoals(goals);

        // Then
        verify(checklistHistoryRepository).saveAll(expectedChecklistHistories);
        verify(checklistStateService).addChecklistStatesFrom(expectedChecklistHistories, goals);
    }

    @Test
    void shouldGetChecklistHistoriesFrom() {
        // Given
        LocalDate today = LocalDate.now();
        Goal goal = testUtil.createGoalWithChecklists(today, 3);

        List<ChecklistHistoryContentStateDTO> historyStates = new ArrayList<>();
        historyStates.add(new ChecklistHistoryContentStateDTO(today, 1L, true, "content1"));
        historyStates.add(new ChecklistHistoryContentStateDTO(today, 2L, true, "content2"));
        historyStates.add(new ChecklistHistoryContentStateDTO(today.plusDays(1), 3L, false, "content3"));
        when(checklistHistoryRepository.findContentStateDTOsByGoal(goal)).thenReturn(historyStates);

        // When
        List<ChecklistHistoryViewDTO> result = checklistHistoryService.getChecklistHistoriesFrom(goal);

        // Then
        verify(checklistHistoryRepository).findContentStateDTOsByGoal(goal);
        assertEquals(2, result.size());

        ChecklistHistoryViewDTO historyView = result.get(0);
        assertEquals(today, historyView.getDate());
        assertEquals(2, historyView.getChecklistStates().size());

        historyView = result.get(1);
        assertEquals(today.plusDays(1), historyView.getDate());
        assertEquals(1, historyView.getChecklistStates().size());
    }

    private ChecklistHistory getChecklistHistory(LocalDate date) {
        ChecklistHistory checklistHistory = new ChecklistHistory();
        checklistHistory.setDate(date);

        return checklistHistory;
    }
}