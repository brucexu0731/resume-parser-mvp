package com.brucexu.springBootBackend.dto.resume;

import java.util.List;

public record EducationDTO(
        String schoolName,
        String degree, //should accomodate for dual degree
        String major, //should accomodate for multiple majors
        String graduationDate,

        //optional
        String grade,
        List<String> honors
) {}
