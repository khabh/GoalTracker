package com.goaltracker.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsernameValidationResponseDTO {
    public static final String DUPLICATION_FIELD = "isDuplicated";
    public static final String INVALID_FORMAT_FIELD = "isInvalidFormat";

    @JsonProperty(DUPLICATION_FIELD)
    private boolean isDuplicated;

    @JsonProperty(INVALID_FORMAT_FIELD)
    private boolean isInvalidFormat;

    public static UsernameValidationResponseDTO invalidFormat() {
        return new UsernameValidationResponseDTO(false, true);
    }

    public static UsernameValidationResponseDTO duplicated() {
        return new UsernameValidationResponseDTO(true, false);
    }

    public static UsernameValidationResponseDTO valid() {
        return new UsernameValidationResponseDTO(false, false);
    }
}
