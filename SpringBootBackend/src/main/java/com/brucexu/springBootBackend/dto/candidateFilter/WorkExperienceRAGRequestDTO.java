package com.brucexu.springBootBackend.dto.candidateFilter;

import java.util.List;

public record WorkExperienceRAGRequestDTO(
        String query,
        List<Long> ids
) {
}
