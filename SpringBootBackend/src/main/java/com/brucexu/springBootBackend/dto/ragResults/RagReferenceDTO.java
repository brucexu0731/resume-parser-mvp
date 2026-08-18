package com.brucexu.springBootBackend.dto.ragResults;

public record RagReferenceDTO(
        Long personalId,
        Long workExperienceId,
        Long contentId,
        Double distance
) {}
