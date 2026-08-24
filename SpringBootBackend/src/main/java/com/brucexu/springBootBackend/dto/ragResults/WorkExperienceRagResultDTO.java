package com.brucexu.springBootBackend.dto.ragResults;

public record WorkExperienceRagResultDTO(
        Long contentId,
        Double distance,

        Long workExperienceId,
        Long personalId,
        Boolean isActive
) {}
