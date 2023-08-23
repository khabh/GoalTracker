package com.goaltracker.checklist.repository;

import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.checklist.dto.PopularCompletedChecklistDTO;
import com.goaltracker.goal.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    @Query("SELECT new com.goaltracker.checklist.dto.PopularCompletedChecklistDTO(c.content, count(cs))" +
    "FROM Checklist c " +
    "INNER JOIN c.checklistStates cs " +
    "WHERE c.goal = :goal AND cs.isCompleted = true " +
    "GROUP BY c.id " +
    "ORDER BY count(cs) DESC")
    List<PopularCompletedChecklistDTO> findPopularCompletedChecklists(@Param("goal") Goal goal);
}
