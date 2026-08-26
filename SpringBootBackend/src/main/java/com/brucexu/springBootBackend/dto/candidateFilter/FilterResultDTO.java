package com.brucexu.springBootBackend.dto.candidateFilter;

public record FilterResultDTO(
        Long personalId,
        String name,
        String phoneNumber,
        String email,
        String latestCompany,
        String latestRole,
        String isCurrent,

        String industry,
        String addDate,
        String updateDate
) {
}
