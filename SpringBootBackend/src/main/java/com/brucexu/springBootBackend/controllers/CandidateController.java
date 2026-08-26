package com.brucexu.springBootBackend.controllers;

import com.brucexu.springBootBackend.dto.candidateFilter.CandidateFiltersDTO;
import com.brucexu.springBootBackend.dto.candidateFilter.FilterResultDTO;
import com.brucexu.springBootBackend.repository.customRepositories.CandidateFilterRepositoryWithVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CandidateController {

    @Autowired
    private CandidateFilterRepositoryWithVector filterWithVector;

    @PostMapping("/candidates/filter")
    public List<FilterResultDTO> filterCandidates(@RequestBody CandidateFiltersDTO filters){

        return filterWithVector.filter(filters);

    }

}
