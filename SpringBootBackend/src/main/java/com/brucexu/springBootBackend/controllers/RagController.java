package com.brucexu.springBootBackend.controllers;

import com.brucexu.springBootBackend.dto.ragResults.WorkExperienceRagResultDTO;
import com.brucexu.springBootBackend.services.RAGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RagController {

    @Autowired
    private RAGService ragService;

    @PostMapping("/rag/work-experiences/test")
    public List<WorkExperienceRagResultDTO> testWorkExperienceRAG(@RequestBody Map<String, String> body) {

        String query = body.get("query");

        return ragService.workExperienceVectorSearch(query, 2);
    }
}
