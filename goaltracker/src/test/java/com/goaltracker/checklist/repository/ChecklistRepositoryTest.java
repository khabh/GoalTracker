package com.goaltracker.checklist.repository;

import com.goaltracker.TestUtil;
import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.domain.ChecklistState;
import com.goaltracker.checklist.dto.PopularCompletedChecklistDTO;
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
class ChecklistRepositoryTest {

    @Autowired
    ChecklistRepository checklistRepository;

    @Autowired
    TestUtil testUtil;

    @Test
    void shouldNotFindChecklistStatesWithNonCurrentDate() {
        // Given
        LocalDate today = LocalDate.now();
        Goal goal = testUtil.createGoalWithChecklists(today, 3);
        ChecklistHistory checklistHistory = testUtil.createChecklistHistory(today.minusDays(1));
        testUtil.createChecklistStates(goal.getChecklists(), checklistHistory);

        // When
        List<ChecklistState> result = checklistRepository.findCurrentDateChecklistStatesByGoalDueDate();

        // Then
        assertEquals(0, result.size());
    }


    @Test
    void shouldFindCurrentDateChecklistStates() {
        // Given
        LocalDate today = LocalDate.now();
        Goal goal = testUtil.createGoalWithChecklists(today, 3);
        ChecklistHistory checklistHistory = testUtil.createChecklistHistory(today);
        List<ChecklistState> expectedChecklistStates = testUtil.createChecklistStates(goal.getChecklists(), checklistHistory);

        // When
        List<ChecklistState> result = checklistRepository.findCurrentDateChecklistStatesByGoalDueDate();

        // Then
        assertEquals(expectedChecklistStates.size(), result.size());
        assertEquals(expectedChecklistStates, result);
    }

    @Test
    void shouldFindPopularCompletedChecklists() {
        // Given
        LocalDate today = LocalDate.now();
        Goal goal = testUtil.createGoal(today);
        ChecklistHistory checklistHistory = testUtil.createChecklistHistory(today);

        int numberOfChecklists = 3;
        List<Checklist> checklists = testUtil.createChecklists(goal, numberOfChecklists);
        for (int i = 0; i < numberOfChecklists; i++) {
            testUtil.addCompletedChecklistStates(checklists.get(i), checklistHistory, numberOfChecklists - i);
        }

        // When
        List<PopularCompletedChecklistDTO> popularCompletedChecklists = checklistRepository.findPopularCompletedChecklists(goal);

        // Then
        assertEquals(numberOfChecklists, popularCompletedChecklists.size());
        IntStream.range(0, numberOfChecklists)
                .forEach(index -> {
                    Checklist checklist = checklists.get(index);
                    PopularCompletedChecklistDTO popularCompletedChecklist = popularCompletedChecklists.get(index);

                    String errorMessage = "Mismatch at index " + index + ": ";
                    assertEquals(checklist.getContent(), popularCompletedChecklist.getContent(), errorMessage + "Content mismatch");
                    assertEquals(numberOfChecklists - index, popularCompletedChecklist.getCount(), errorMessage + "Count mismatch");
                });
    }
}