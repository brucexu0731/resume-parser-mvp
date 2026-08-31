package com.brucexu.springBootBackend.dto.candidateFilter;

import java.util.List;

public record AgentCandidateFiltersDTO(
        List<Long> ids,

        List<String> name,
        List<String> phoneNumber,
        List<String> email,
        List<String> highestDegree, //list of options
        List<String> industry,
        List<String> addBy,

        List<String> company,
        String companyStatus, //all or current
        List<String> title,
        String titleStatus //all or current
) {
}
