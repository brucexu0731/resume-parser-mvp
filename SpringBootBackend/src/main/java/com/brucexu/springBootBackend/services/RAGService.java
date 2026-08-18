package com.brucexu.springBootBackend.services;

import com.brucexu.springBootBackend.dto.ragResults.RagContextDTO;
import com.brucexu.springBootBackend.dto.ragResults.RagReferenceDTO;
import com.brucexu.springBootBackend.dto.ragResults.RagResultDTO;
import com.brucexu.springBootBackend.repository.WorkExperienceContentRespository;
import com.brucexu.springBootBackend.repository.projection.VectorSearchProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class RAGService {

    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private WorkExperienceContentRespository workExperienceContentRespository;


    // To-do: write a funcation that normalizes a question to lower case
    private String normalizeQuery(String query){
        return query.toLowerCase().trim().replaceAll("\\s+", " ");
    }

    // This function checks if the rag search are too irrelevant
    private boolean hasRelevantContext(List<VectorSearchProjection> searchResults){
        if (searchResults == null || searchResults.isEmpty()){
            return false;
        }
        double distance = searchResults.get(0).getDistance();

        return distance <= 0.65;
    }


    // Notes:
    // 1. Rag filter should not filter too aggressively before LLM, start with 0.65 cosine distance with
    // around 10 chunks with loose threshold, and let the llm/reranker decide
    // 2. LangSmith evaluation can help you tune later: query → retrieved candidates →
    // relevance score → determine optimal distance threshold.

    public List<RagResultDTO> vectorSearch(String query, int topK) {

        float[] queryEmbedding = embeddingService.embed(query);
        String vector = embeddingService.toVectorString(queryEmbedding);

        List<VectorSearchProjection> searchResults = workExperienceContentRespository.vectorSearch(vector, topK);

        return searchResults.stream()
                .map(result -> new RagResultDTO(
                        new RagContextDTO(
                                result.getPersonName(),
                                result.getIsActive(),
                                result.getIndustry(),
                                result.getLatestCompany(),
                                result.getCompanyName(),
                                result.getTitle(),
                                result.getEndDate(),
                                result.getEmploymentType(),
                                result.getContent()
                        ),
                        new RagReferenceDTO(
                                result.getPersonalId(),
                                result.getWorkExperienceId(),
                                result.getContentId(),
                                result.getDistance()
                        )
                ))
                .toList();
    }

    // To-do: set up aws elasticache connection and check cache for question



}
