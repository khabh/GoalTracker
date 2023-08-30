package com.goaltracker.checklist.util;

import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.dto.ChecklistHistoryContentStateDTO;
import com.goaltracker.checklist.dto.ChecklistHistoryViewDTO;
import com.goaltracker.checklist.dto.vo.ChecklistStateVO;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChecklistHistoryConverter {
    private static final ModelMapper stateConverter = new ModelMapper();

    private ChecklistHistoryConverter() {}

    public static ChecklistHistory from(LocalDate localDate) {
        ChecklistHistory checklistHistory = new ChecklistHistory();
        checklistHistory.setDate(localDate);

        return checklistHistory;
    }

    public static List<ChecklistHistory> toChecklistHistories(LocalDate localDate, int size) {
        List<ChecklistHistory> checklistHistories = new ArrayList<>();
        IntStream.range(0, size)
                .forEach(index -> checklistHistories.add(ChecklistHistoryConverter.from(localDate)));

        return checklistHistories;
    }

    private static ChecklistHistoryViewDTO toViewDTO(LocalDate date, List<ChecklistHistoryContentStateDTO> contentStateDTOs) {
        List<ChecklistStateVO> checklistStates = contentStateDTOs.stream()
                .map(contentStateDTO -> stateConverter.map(contentStateDTO, ChecklistStateVO.class))
                .collect(Collectors.toList());

        ChecklistHistoryViewDTO checklistHistoryViewDTO = new ChecklistHistoryViewDTO();
        checklistHistoryViewDTO.setDate(date);
        checklistHistoryViewDTO.setChecklistStates(checklistStates);

        return checklistHistoryViewDTO;
    }

    public static List<ChecklistHistoryViewDTO> toViewDTOs(List<ChecklistHistoryContentStateDTO> contentStates) {
        Map<LocalDate, List<ChecklistHistoryContentStateDTO>> groupedContentStates = new LinkedHashMap<>();
        for (ChecklistHistoryContentStateDTO contentStateDTO : contentStates) {
            groupedContentStates.computeIfAbsent(contentStateDTO.getDate(), k -> new ArrayList<>())
                    .add(contentStateDTO);
        }

        return groupedContentStates.keySet()
                .stream()
                .map(date -> toViewDTO(date, groupedContentStates.get(date)))
                .collect(Collectors.toList());
    }
}
