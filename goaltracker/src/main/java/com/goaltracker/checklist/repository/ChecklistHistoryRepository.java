package com.goaltracker.checklist.repository;

import com.goaltracker.checklist.domain.ChecklistHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistHistoryRepository extends JpaRepository<ChecklistHistory, Long> {
}
