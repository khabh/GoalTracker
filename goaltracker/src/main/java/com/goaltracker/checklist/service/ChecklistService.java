package com.goaltracker.checklist.service;

import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.checklist.dto.PopularCompletedChecklistDTO;
import com.goaltracker.goal.domain.Goal;

import java.util.List;

public interface ChecklistService {

    void createChecklists(CreateChecklistsDTO createChecklistsDTO, Goal goal);
    List<PopularCompletedChecklistDTO> getPopularCompletedChecklists(Goal goal);
}
