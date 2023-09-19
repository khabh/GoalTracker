package com.goaltracker.goal.service;

import com.goaltracker.checklist.domain.ChecklistState;
import com.goaltracker.checklist.dto.ChecklistHistoryViewDTO;
import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.checklist.dto.PopularCompletedChecklistDTO;
import com.goaltracker.checklist.service.ChecklistHistoryService;
import com.goaltracker.checklist.service.ChecklistService;
import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.dto.ActiveGoalDTO;
import com.goaltracker.goal.dto.CreateGoalDTO;
import com.goaltracker.goal.dto.GoalPerformanceDTO;
import com.goaltracker.goal.exception.GoalNotFoundException;
import com.goaltracker.goal.repository.GoalRepository;
import com.goaltracker.goal.util.GoalConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoalServiceImpl implements GoalService {

    private final ModelMapper modelMapper = new ModelMapper();
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
    public List<ActiveGoalDTO> getUserActiveGoals(Long userId) {
        List<ChecklistState> checklistStates = checklistService.getDailyChecklistStatesForUserActiveGoals(userId);
        Map<Goal, List<ChecklistState>> groupedChecklistStates = groupChecklistStatesByGoal(checklistStates);

        return groupedChecklistStates.entrySet()
                .stream()
                .map(activeGoal -> GoalConverter.toActiveGoalDTO(activeGoal.getKey(), activeGoal.getValue()))
                .collect(Collectors.toList());
    }

    private Map<Goal, List<ChecklistState>> groupChecklistStatesByGoal(List<ChecklistState> checklistStates) {
        Map<Goal, List<ChecklistState>> groupedChecklistStates = new HashMap<>();
        for (ChecklistState checklistState : checklistStates) {
            Goal goal = checklistState.getChecklist().getGoal();
            groupedChecklistStates.computeIfAbsent(goal, g -> new ArrayList<>()).add(checklistState);
        }

        return groupedChecklistStates;
    }

    @Override
    public void createGoal(CreateGoalDTO createGoalDTO, CreateChecklistsDTO createChecklistsDTO) {
        Goal goal = modelMapper.map(createGoalDTO, Goal.class);
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
