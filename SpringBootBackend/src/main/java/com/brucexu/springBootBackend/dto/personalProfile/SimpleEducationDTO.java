package com.brucexu.springBootBackend.dto.personalProfile;

public record SimpleEducationDTO(
        String schoolName,
        String degree, //should accomodate for dual degree
        String major, //should accomodate for multiple majors
        String graduationDate
) {}
