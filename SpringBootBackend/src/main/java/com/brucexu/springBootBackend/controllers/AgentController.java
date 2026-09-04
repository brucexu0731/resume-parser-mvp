package com.brucexu.springBootBackend.controllers;

import com.brucexu.springBootBackend.dto.candidateFilter.AgentCandidateFiltersDTO;
import com.brucexu.springBootBackend.dto.candidateFilter.WorkExperienceRAGRequestDTO;
import com.brucexu.springBootBackend.dto.personalProfile.SimplePersonProfileDTO;
import com.brucexu.springBootBackend.dto.ragResults.WorkExperienceRagResultDTO;
import com.brucexu.springBootBackend.entity.Personal;
import com.brucexu.springBootBackend.repository.PersonalRepository;
import com.brucexu.springBootBackend.repository.customRepositories.AgentSQLCandidateFilterRepositoryMultipleFields;
import com.brucexu.springBootBackend.repository.customRepositories.AgentSQLCandidateFilterRepositoryMultipleFieldsTest;
import com.brucexu.springBootBackend.repository.projections.SimplePersonProfileProjection;
import com.brucexu.springBootBackend.services.CandidateInfoService;
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
    private AgentSQLCandidateFilterRepositoryMultipleFields sqlFilter;
    @Autowired
    private AgentSQLCandidateFilterRepositoryMultipleFieldsTest sqlFilterTest;

    @Autowired
    private CandidateInfoService candidateInfoService;

    @PostMapping("/agents/rag/work-experiences")
    public List<WorkExperienceRagResultDTO> workExperienceRAG(@RequestBody WorkExperienceRAGRequestDTO body) {

        String query = body.query();
        List<Long> filterIds = body.ids();
        boolean filterByIds = filterIds != null && !filterIds.isEmpty();

        return ragService.workExperienceVectorSearch(query, 100, filterIds, filterByIds);
    }

    @PostMapping("/agents/filter")
    public List<Long> filterCandidates(@RequestBody AgentCandidateFiltersDTO filters){

        return sqlFilter.filter(filters);
    }

    @PostMapping("/agents/filter/test")
    public List<String> filterCandidatesTest(@RequestBody AgentCandidateFiltersDTO filters){

        return sqlFilterTest.filter(filters);
    }

    @PostMapping("/agents/candidate-basic-info")
    public List<SimplePersonProfileDTO> getBasicPersonalInfo(@RequestBody Map<String, List<Long>> body){
        List<Long> ids = body.get("ids");
        return candidateInfoService.getSimpleProfiles(ids);
    }
}

