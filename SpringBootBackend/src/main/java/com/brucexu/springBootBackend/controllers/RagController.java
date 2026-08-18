package com.brucexu.springBootBackend.controllers;

import com.brucexu.springBootBackend.dto.ragResults.RagContextDTO;
import com.brucexu.springBootBackend.dto.ragResults.RagReferenceDTO;
import com.brucexu.springBootBackend.dto.ragResults.RagResultDTO;
import com.brucexu.springBootBackend.repository.*;
import com.brucexu.springBootBackend.repository.projection.VectorSearchProjection;
import com.brucexu.springBootBackend.services.EmbeddingService;
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

    @PostMapping("/rag/test")
    public List<RagResultDTO> testRAG(@RequestBody Map<String, String> body) {

        String query = body.get("query");

        return ragService.vectorSearch(query, 2);
    }
}
