package com.goaltracker.goal.service;

import com.goaltracker.TestUtil;
import com.goaltracker.checklist.domain.ChecklistState;
import com.goaltracker.checklist.service.ChecklistService;
import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.dto.ActiveGoalDTO;
import com.goaltracker.goal.repository.GoalRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class GoalServiceImplTest {

    @Autowired
    TestUtil testUtil;

    @Mock
    GoalRepository goalRepository;

    @Mock
    ChecklistService checklistService;

    @InjectMocks
    GoalServiceImpl goalService;

    private List<ChecklistState> getChecklistStates(int numberOfChecklistState) {
        LocalDate today = LocalDate.now();
        Goal goal = testUtil.createGoalWithChecklists(today, numberOfChecklistState);

        return testUtil.createChecklistStates(goal.getChecklists(), testUtil.createChecklistHistory(today));
    }

    @Test
    void testGetActiveGoals() {
        // Given
        List<ChecklistState> checklistStates = getChecklistStates(2);
        checklistStates.addAll(getChecklistStates(3));
        when(checklistService.getDailyChecklistStatesToActiveGoals()).thenReturn(checklistStates);

        // When
        List<ActiveGoalDTO> result = goalService.getActiveGoals();

        // Then
        assertEquals(2, result.size());
        for (ActiveGoalDTO activeGoal : result) {
            int expectedSize = (int)checklistStates.stream()
                    .filter(checklistState -> checklistState.getChecklist().getGoal().getId().equals(activeGoal.getId()))
                    .count();
            assertEquals(expectedSize, activeGoal.getChecklistStates().size());
        }
    }

    @Test
    void createGoal() {
    }

    @Test
    void getGoalPerformance() {
    }
}