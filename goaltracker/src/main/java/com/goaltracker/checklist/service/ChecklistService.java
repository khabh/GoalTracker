package com.goaltracker.checklist.service;

import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.goal.domain.Goal;

public interface ChecklistService {

    public void createChecklists(CreateChecklistsDTO createChecklistsDTO, Goal goal);
}
