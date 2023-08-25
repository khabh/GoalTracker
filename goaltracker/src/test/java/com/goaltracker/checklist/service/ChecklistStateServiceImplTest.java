package com.goaltracker.checklist.service;

import com.goaltracker.TestUtil;
import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.domain.ChecklistState;
import com.goaltracker.checklist.repository.ChecklistStateRepository;
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

import static org.mockito.Mockito.verify;

@SpringBootTest
class ChecklistStateServiceImplTest {

    @Mock
    private ChecklistStateRepository checklistStateRepository;

    @InjectMocks
    private ChecklistStateServiceImpl checklistStateService;

    @Autowired
    private TestUtil testUtil;

    private List<ChecklistState> getChecklistStates(List<Goal> goals, List<ChecklistHistory> checklistHistories) {
        return IntStream.range(0, goals.size())
                .mapToObj(index -> {
                    ChecklistHistory checklistHistory = checklistHistories.get(index);
                    return goals.get(index).getChecklists()
                            .stream()
                            .map(checklist -> {
                                ChecklistState checklistState = new ChecklistState();
                                checklistState.setChecklist(checklist);
                                checklistState.setChecklistHistory(checklistHistory);
                                return checklistState;
                            })
                            .collect(Collectors.toList());
                }).flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Test
    void shouldAddChecklistStates() {
        // Given
        LocalDate today = LocalDate.now();
        List<Goal> goals = IntStream.range(1, 4)
                .mapToObj(count -> testUtil.createGoalWithChecklists(today.plusDays(count), count))
                .collect(Collectors.toList());
        List<ChecklistHistory> checklistHistories = IntStream.range(0, 3)
                .mapToObj(count -> testUtil.createChecklistHistory(today))
                .collect(Collectors.toList());
        List<ChecklistState> expectedChecklistStates = getChecklistStates(goals, checklistHistories);

        // When
        checklistStateService.addChecklistStatesFrom(checklistHistories, goals);

        // Then
        verify(checklistStateRepository).saveAll(expectedChecklistStates);
    }
}