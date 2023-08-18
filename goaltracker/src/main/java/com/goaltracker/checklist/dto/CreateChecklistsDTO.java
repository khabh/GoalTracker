package com.goaltracker.checklist.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateChecklistsDTO {
    private List<String> checklists;

    @Override
    public String toString() {
        return "CreateChecklistsDTO{" +
                "checklists=" + checklists +
                '}';
    }
}
