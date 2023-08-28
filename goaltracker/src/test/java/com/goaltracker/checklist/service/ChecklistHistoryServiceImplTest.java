package com.goaltracker.checklist.service;

import com.goaltracker.TestUtil;
import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.dto.ChecklistHistoryContentStateDTO;
import com.goaltracker.checklist.dto.ChecklistHistoryViewDTO;
import com.goaltracker.checklist.repository.ChecklistHistoryRepository;
import com.goaltracker.goal.domain.Goal;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest

class ChecklistHistoryServiceImplTest {

    @Autowired
    TestUtil testUtil;

    @Mock
    ChecklistStateService checklistStateService;

    @Mock
    ChecklistHistoryRepository checklistHistoryRepository;

    @InjectMocks
    ChecklistHistoryServiceImpl checklistHistoryService;

    private ChecklistHistory getChecklistHistory(LocalDate date) {
        ChecklistHistory checklistHistory = new ChecklistHistory();
        checklistHistory.setDate(date);

        return checklistHistory;
    }

    private List<ChecklistHistoryContentStateDTO> getChecklistHistoryContentStates(List<Checklist> checklists) {
        return checklists.stream()
                .map(checklist ->
                    new ChecklistHistoryContentStateDTO(LocalDate.now(), checklist.getId(), true, checklist.getContent()))
                .collect(Collectors.toList());
    }

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
        Goal goal = testUtil.createGoalWithChecklists(LocalDate.now(), 3);
        List<ChecklistHistoryContentStateDTO> checklistHistoryContentStates = getChecklistHistoryContentStates(goal.getChecklists());
        when(checklistHistoryRepository.findContentStateDTOsByGoal(goal)).thenReturn(checklistHistoryContentStates);

       // When
        List<ChecklistHistoryViewDTO> result = checklistHistoryService.getChecklistHistoriesFrom(goal);

        // Then
        verify(checklistHistoryRepository).findContentStateDTOsByGoal(goal);
        assertEquals(1, result.size());
    }
}