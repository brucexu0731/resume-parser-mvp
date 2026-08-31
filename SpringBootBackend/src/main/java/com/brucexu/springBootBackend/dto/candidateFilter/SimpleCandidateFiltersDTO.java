package com.brucexu.springBootBackend.dto.candidateFilter;

public record CandidateFiltersDTO(
        String name,
        String phoneNumber,
        String email,
        String highestDegree, //list of options
        String industry,
        String addBy,

        String company,
        String companyStatus, //all or current
        String title,
        String titleStatus, //all or current

        String vectorQuery

) {
}
