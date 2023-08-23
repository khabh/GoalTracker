package com.goaltracker.goal.service;

import com.goaltracker.checklist.dto.ChecklistHistoryViewDTO;
import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.checklist.dto.PopularCompletedChecklistDTO;
import com.goaltracker.checklist.service.ChecklistHistoryService;
import com.goaltracker.checklist.service.ChecklistService;
import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.dto.CreateGoalDTO;
import com.goaltracker.goal.dto.GoalPerformanceDTO;
import com.goaltracker.goal.exception.GoalNotFoundException;
import com.goaltracker.goal.repository.GoalRepository;
import com.goaltracker.goal.util.GoalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final ChecklistService checklistService;
    private final ChecklistHistoryService checklistHistoryService;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository, ChecklistService checklistService, ChecklistHistoryService checklistHistoryService) {
        this.goalRepository = goalRepository;
        this.checklistService = checklistService;
        this.checklistHistoryService = checklistHistoryService;
    }

    @Override
    public void createGoal(CreateGoalDTO createGoalDTO, CreateChecklistsDTO createChecklistsDTO) {
        Goal goal = GoalConverter.convertToEntity(createGoalDTO);
        goalRepository.save(goal);
        checklistService.createChecklists(createChecklistsDTO, goal);
    }

    @Override
    public List<Goal> getGoalsWithDueDateNotPassed() {
        return goalRepository.getGoalsByDueDateIsGreaterThanEqual(LocalDate.now());
    }

    @Override
    public GoalPerformanceDTO getGoalPerformance(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new GoalNotFoundException(goalId));
        List<ChecklistHistoryViewDTO> checklistHistories = checklistHistoryService.getChecklistHistoriesFrom(goal);
        List<PopularCompletedChecklistDTO> popularCompletedChecklists = checklistService.getPopularCompletedChecklists(goal);

        return GoalPerformanceDTO.builder()
                .goalId(goal.getId())
                .name(goal.getName())
                .description(goal.getDescription())
                .progress(goal.getProgress())
                .checklistHistories(checklistHistories)
                .popularCompletedChecklists(popularCompletedChecklists)
                .build();
    }
}
