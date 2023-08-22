package com.goaltracker.checklist.repository;

import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.dto.ChecklistHistoryContentStateDTO;
import com.goaltracker.goal.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistHistoryRepository extends JpaRepository<ChecklistHistory, Long> {
    @Query("SELECT new com.goaltracker.checklist.dto.ChecklistHistoryContentStateDTO(ch.date, cs.id, cs.isCompleted, cs.checklist.content) " +
    "FROM ChecklistState cs " +
    "INNER JOIN cs.checklistHistory ch " +
    "WHERE cs.checklist.goal = :goal ")
    List<ChecklistHistoryContentStateDTO> findContentStateDTOsByGoal(@Param("goal") Goal goal);
}
