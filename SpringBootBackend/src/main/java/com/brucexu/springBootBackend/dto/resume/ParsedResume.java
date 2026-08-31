package com.brucexu.springBootBackend.dto.resume;

import java.util.List;

public record ParsedResume(
        String s3Key,
        PersonalDTO personal,
        List<WorkExperienceDTO> work,
        List<EducationDTO> education
) {}
