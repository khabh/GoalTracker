package com.goaltracker.checklist.repository;

import com.goaltracker.TestUtil;
import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.domain.ChecklistState;
import com.goaltracker.checklist.dto.ChecklistHistoryContentStateDTO;
import com.goaltracker.goal.domain.Goal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestUtil.class)
class ChecklistHistoryRepositoryTest {

    @Autowired
    ChecklistHistoryRepository checklistHistoryRepository;

    @Autowired
    TestUtil testUtil;

    @Test
    void findContentStateDTOsByGoal() {
        // Given
        LocalDate today = LocalDate.now();
        ChecklistHistory checklistHistory = testUtil.createChecklistHistory(today);
        Goal goal = testUtil.createGoal(today);
        List<Checklist> checklists = testUtil.createChecklists(goal, 3);
        List<ChecklistState> checklistStates = testUtil.createChecklistStates(checklists, checklistHistory);

        // When
        List<ChecklistHistoryContentStateDTO> checklistHistoryContentStates = checklistHistoryRepository.findContentStateDTOsByGoal(goal);

        // Then
        final int numberOfContentStates = 3;
        assertEquals(numberOfContentStates, checklistHistoryContentStates.size());

        IntStream.range(0, numberOfContentStates).forEach(index -> {
            ChecklistState checklistState = checklistStates.get(index);
            ChecklistHistoryContentStateDTO checklistHistoryContentState = checklistHistoryContentStates.get(index);
            assertChecklistStateAndDTO(checklistState, checklistHistoryContentState, today);
        });
    }

    private void assertChecklistStateAndDTO(ChecklistState checklistState, ChecklistHistoryContentStateDTO dto, LocalDate today) {
        assertEquals(checklistState.isCompleted(), dto.isCompleted());
        assertEquals(checklistState.getId(), dto.getId());
        assertEquals(checklistState.getChecklist().getContent(), dto.getContent());
        assertEquals(today, dto.getDate());
    }
}