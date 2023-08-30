package com.goaltracker.goal.repository;


import com.goaltracker.goal.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> getGoalsByDueDateIsGreaterThanEqual(LocalDate currentDate);
}
