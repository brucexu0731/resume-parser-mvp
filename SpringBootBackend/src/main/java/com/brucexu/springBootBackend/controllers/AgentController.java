package com.brucexu.springBootBackend.controllers;

import com.brucexu.springBootBackend.dto.candidateFilter.CandidateFiltersDTO;
import com.brucexu.springBootBackend.dto.candidateFilter.FilterResultDTO;
import com.brucexu.springBootBackend.dto.ragResults.WorkExperienceRagResultDTO;
import com.brucexu.springBootBackend.repository.customRepositories.CandidateFilterRepository;
import com.brucexu.springBootBackend.services.RAGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AgentController {

    @Autowired
    private RAGService ragService;
    @Autowired
    private CandidateFilterRepository sqlFilter;

    @PostMapping("/agents/rag/work-experiences")
    public List<WorkExperienceRagResultDTO> workExperienceRAG(@RequestBody Map<String, String> body) {

        String query = body.get("query");

        return ragService.workExperienceVectorSearch(query, 2);
    }

    @PostMapping("/agents/filter")
    public List<Long> filterCandidates(@RequestBody CandidateFiltersDTO filters){

        return sqlFilter.filter(filters);
    }
}
