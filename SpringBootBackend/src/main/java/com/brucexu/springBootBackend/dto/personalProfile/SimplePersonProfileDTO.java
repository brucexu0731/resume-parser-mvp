package com.brucexu.springBootBackend.dto.personalProfile;

import java.util.List;

public record SimplePersonProfileDTO(
        Long id,
        String name,
        String phoneNumber,
        String email,

        List<String> skills,
        String Industry,
        Boolean needVisa,
        String addDate,
        String addBy,
        String updateDate,
        String updateBy,
        String candidateSource,
        String preferredLocation,
        String preferredIndustry,
        String preferredBaseSalary,
        String preferredAnnualPackage,
        String noticePeriod,
        String additionalInfo,
        String motivation,

        List<SimpleWorkExperienceDTO> workExperience,
        List<SimpleEducationDTO> educations


) {
}
