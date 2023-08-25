package com.goaltracker.goal.repository;

import com.goaltracker.TestUtil;
import com.goaltracker.goal.domain.Goal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestUtil.class)
class GoalRepositoryTest {

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    TestUtil testUtil;

    @Test
    void shouldNotFindGoalsWithPastDueDate() {
        // Given
        testUtil.createGoal(LocalDate.now().minusDays(1));

        // When
        List<Goal> goals = goalRepository.getGoalsByDueDateIsGreaterThanEqual(LocalDate.now());

        // Then
        assertEquals(0, goals.size());
    }

    @Test
    void shouldFindGoalsWithCurrentDueDate() {
        // Given
        Goal goalWithCurrentDueDate = testUtil.createGoal(LocalDate.now().plusDays(1));

        // When
        List<Goal> goals = goalRepository.getGoalsByDueDateIsGreaterThanEqual(LocalDate.now());

        // Then
        assertEquals(1, goals.size());
        assertEquals(goalWithCurrentDueDate, goals.get(0));
    }

    @Test
    void shouldFindGoalsWithFutureDueDate() {
        // Given
        Goal goalWithFutureDueDate = testUtil.createGoal(LocalDate.now().plusDays(1));

        // When
        List<Goal> goals = goalRepository.getGoalsByDueDateIsGreaterThanEqual(LocalDate.now());

        // Then
        assertEquals(1, goals.size());
        assertEquals(goalWithFutureDueDate, goals.get(0));
    }
}