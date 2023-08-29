package com.goaltracker.goal.service;

import com.goaltracker.TestUtil;
import com.goaltracker.checklist.domain.ChecklistState;
import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.checklist.service.ChecklistHistoryService;
import com.goaltracker.checklist.service.ChecklistService;
import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.dto.ActiveGoalDTO;
import com.goaltracker.goal.dto.CreateGoalDTO;
import com.goaltracker.goal.repository.GoalRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class GoalServiceImplTest {

    @Autowired
    TestUtil testUtil;

    @Mock
    GoalRepository goalRepository;

    @Mock
    ChecklistService checklistService;

    @Mock
    ChecklistHistoryService checklistHistoryService;

    @InjectMocks
    GoalServiceImpl goalService;

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
            Long goalId = activeGoal.getId();
            int expectedSize = (int)checklistStates.stream()
                    .filter(checklistState -> checklistState.getChecklist().getGoal().getId().equals(goalId))
                    .count();
            assertEquals(expectedSize, activeGoal.getChecklistStates().size());
        }
    }

    @Test
    void shouldCreateGoal() {
        // Given
        CreateGoalDTO createGoalDTO = getCreateGoalDTO();
        CreateChecklistsDTO createChecklistsDTO = new CreateChecklistsDTO();
        createChecklistsDTO.setChecklists(List.of("checklist1", "checklist2"));
        Goal goal = new Goal();
        goal.setName(createGoalDTO.getName());
        goal.setDescription(createGoalDTO.getDescription());
        goal.setDueDate(createGoalDTO.getDueDate());

        // When
        goalService.createGoal(createGoalDTO, createChecklistsDTO);

        // Then
        verify(goalRepository).save(goal);
        verify(checklistService).createChecklists(createChecklistsDTO, goal);
    }

    @Test
    void shouldGetGoalPerformance() {
        // Given
        Goal goal = testUtil.createGoal(LocalDate.now());
        when(goalRepository.findById(goal.getId())).thenReturn(Optional.of(goal));
        when(checklistHistoryService.getChecklistHistoriesFrom(any())).thenReturn(new ArrayList<>());
        when(checklistService.getPopularCompletedChecklists(any())).thenReturn(new ArrayList<>());

        // When
        goalService.getGoalPerformance(goal.getId());

        // Then
        verify(checklistHistoryService).getChecklistHistoriesFrom(goal);
        verify(checklistService).getPopularCompletedChecklists(goal);
    }

    private CreateGoalDTO getCreateGoalDTO() {
        CreateGoalDTO goalDTO = new CreateGoalDTO();
        goalDTO.setName("goalName");
        goalDTO.setDescription("goal description");
        goalDTO.setDueDate(LocalDate.now().plusDays(1));

        return goalDTO;
    }

    private List<ChecklistState> getChecklistStates(int numberOfChecklistState) {
        LocalDate today = LocalDate.now();
        Goal goal = testUtil.createGoalWithChecklists(today, numberOfChecklistState);

        return testUtil.createChecklistStates(goal.getChecklists(), testUtil.createChecklistHistory(today));
    }
}