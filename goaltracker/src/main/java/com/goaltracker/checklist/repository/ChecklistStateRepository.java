package com.goaltracker.checklist.repository;

import com.goaltracker.checklist.domain.ChecklistState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistStateRepository extends JpaRepository<ChecklistState, Long> {
}
