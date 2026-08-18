package com.brucexu.springBootBackend.dto.ragResults;

public record RagContextDTO(
        String personName,
        Boolean isActive,
        String industry,
        String latestCompany,

        String companyName,
        String title,
        String endDate,
        String employmentType,
        String content
) {}
