package com.brucexu.springBootBackend.dto.personalProfile;

public record SimpleWorkExperienceDTO(
        String company,
        String startDate,
        String endDate
) {}
