package com.goaltracker.checklist.service;

import com.goaltracker.TestUtil;
import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.checklist.repository.ChecklistRepository;
import com.goaltracker.goal.domain.Goal;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
class ChecklistServiceImplTest {

    @Mock
    ChecklistRepository checklistRepository;

    @InjectMocks
    ChecklistServiceImpl checklistService;

    @Autowired
    TestUtil testUtil;

    @Test
    void shouldCreateChecklists() {
        // Given
        int numberOfChecklists = 3;
        Goal goal = testUtil.createGoal(LocalDate.now());
        CreateChecklistsDTO createChecklistsDTO = new CreateChecklistsDTO();
        List<String> contents = IntStream.range(0, numberOfChecklists)
                .mapToObj(index -> "content" + index)
                .collect(Collectors.toList());
        createChecklistsDTO.setChecklists(contents);
        List<Checklist> expectedChecklists = contents.stream()
                .map(content -> {
                    Checklist checklist = new Checklist();
                    checklist.setGoal(goal);
                    checklist.setContent(content);
                    return checklist;
                }).collect(Collectors.toList());

        // When
        checklistService.createChecklists(createChecklistsDTO, goal);

        // Then
        verify(checklistRepository).saveAll(expectedChecklists);
    }

    @Test
    void shouldFilterBlankChecklist() {
        // Given
        Goal goal = testUtil.createGoal(LocalDate.now());
        CreateChecklistsDTO createChecklistsDTO = new CreateChecklistsDTO();
        List<String> contents = new ArrayList<>();
        contents.add("content");
        contents.add("     ");
        contents.add("");
        createChecklistsDTO.setChecklists(contents);
        List<Checklist> expectedChecklist = new ArrayList<>();
        Checklist checklist = new Checklist();
        checklist.setGoal(goal);
        checklist.setContent(contents.get(0));
        expectedChecklist.add(checklist);

        // When
        checklistService.createChecklists(createChecklistsDTO, goal);

        // Then
        verify(checklistRepository).saveAll(expectedChecklist);
    }
}