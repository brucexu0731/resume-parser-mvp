package com.brucexu.springBootBackend.services;

import com.brucexu.springBootBackend.dto.ragResults.WorkExperienceRagResultDTO;
import com.brucexu.springBootBackend.repository.WorkExperienceContentRespository;
import com.brucexu.springBootBackend.repository.projections.WorkExperienceVectorSearchProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    // No need anymore, the SQL query automatically filters
//    private boolean hasRelevantContext(List<WorkExperienceVectorSearchProjection> searchResults){
//        if (searchResults == null || searchResults.isEmpty()){
//            return false;
//        }
//        double distance = searchResults.get(0).getDistance();
//
//        return distance <= 0.65;
//    }


    // Notes:
    // 1. Rag filter should not filter too aggressively before LLM, start with 0.65 cosine distance with
    // around 10 chunks with loose threshold, and let the llm/reranker decide
    // 2. LangSmith evaluation can help you tune later: query → retrieved candidates →
    // relevance score → determine optimal distance threshold.

    public List<WorkExperienceRagResultDTO> workExperienceVectorSearch(String query, int topK, List<Long> ids, boolean filterByIds) {

        float[] queryEmbedding = embeddingService.embed(query);
        String vector = embeddingService.toVectorString(queryEmbedding);

        List<WorkExperienceVectorSearchProjection> searchResults = workExperienceContentRespository.vectorSearch(vector, topK, ids, filterByIds);

        return searchResults.stream()
                .map(result -> new WorkExperienceRagResultDTO(
                            result.getContentId(),
                            result.getDistance(),
                            result.getWorkExperienceId(),
                            result.getPersonalId(),
                            result.getContent(),
                            result.getCompanyName()
                        )
                    )
                .toList();
    }

    // To-do: set up aws elasticache connection and check cache for question



}
