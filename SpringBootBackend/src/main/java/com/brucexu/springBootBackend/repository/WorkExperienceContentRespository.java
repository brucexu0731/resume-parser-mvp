package com.brucexu.springBootBackend.repository;

import com.brucexu.springBootBackend.entity.WorkExperience;
import com.brucexu.springBootBackend.entity.WorkExperienceContent;
import com.brucexu.springBootBackend.repository.projection.WorkExperienceVectorSearchProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkExperienceContentRespository
        extends JpaRepository<WorkExperienceContent, Long> {

    @Query(value = """
            SELECT
                p.personal_id AS personalId,
                p.active AS isActive,
                we.work_experience_id AS workExperienceId,
                wec.id AS contentId,
                wec.embedding <=> CAST(:queryVector AS vector) AS distance
        
            FROM work_experience_content wec
            JOIN work_experience we
                ON wec.work_experience_id = we.work_experience_id
            JOIN personal p
                ON we.personal_id = p.personal_id
                            
            WHERE p.active = true 
                AND wec.embedding <=> CAST(:queryVector AS vector) < 0.65
        
            ORDER BY distance
                        
            LIMIT :topK
            """,
            nativeQuery = true)
    List<WorkExperienceVectorSearchProjection> vectorSearch(
            @Param("queryVector") String queryVector,
            @Param("topK") int topK
    );

}
