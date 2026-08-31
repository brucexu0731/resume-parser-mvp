package com.brucexu.springBootBackend.dto.resume;

import java.util.List;

public record PersonalDTO(
        String name,
        String phoneNumber,
        String email,
        String resumeS3Key,
        String latestCompany,
        String latestRole,
        String isCurrent,
        String highestDegree,
        String highestDegreeSchool,
        String highestDegreeGraduationDate,
        List<String> highestDegreeMajor,
        List<String> skills,

        //optional personal
        String age,
        String sex,
        String gender,

        //optional recruiting info
        Boolean active,
        String Industry, //manual for now, AI generated later, should be a list of pre-defined industries
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

        // will add more in the future
        String additionalInfo,
        String motivation

) {}
