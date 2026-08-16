package com.brucexu.springBootBackend.dto.resume;

import java.util.List;

public record WorkExperienceDTO(
        String companyName,
        String title,
        String employmentType,
        String start,
        String end,
        List<String> contents,

        // optional
        String location,
        String Industry // Manual for now, will let AI have best guess later
) {}
