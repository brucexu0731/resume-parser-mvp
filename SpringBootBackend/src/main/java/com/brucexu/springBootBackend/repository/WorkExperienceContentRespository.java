package com.brucexu.springBootBackend.repository;

import com.brucexu.springBootBackend.entity.WorkExperience;
import com.brucexu.springBootBackend.entity.WorkExperienceContent;
import com.brucexu.springBootBackend.repository.projection.VectorSearchProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkExperienceContentRespository
        extends JpaRepository<WorkExperienceContent, Long> {

    @Query(value = """
            SELECT
                wec.id AS contentId,
                wec.content AS content,
                wec.embedding <=> CAST(:queryVector AS vector) AS distance,
        
                we.work_experience_id AS workExperienceId,
                we.company_name AS companyName,
                we.title AS title,
                we.end_date AS endDate,
                we.employment_type AS employmentType,
        
                p.personal_id AS personalId,
                p.name AS personName,
                p.active AS isActive,
                p.industry AS industry,
                p.latest_company AS latestCompany
        
            FROM work_experience_content wec
        
            JOIN work_experience we
                ON wec.work_experience_id = we.work_experience_id
        
            JOIN personal p
                ON we.personal_id = p.personal_id
        
            ORDER BY wec.embedding <=> CAST(:queryVector AS vector)
                        
            LIMIT :topK
            """,
            nativeQuery = true)
    List<VectorSearchProjection> vectorSearch(
            @Param("queryVector") String queryVector,
            @Param("topK") int topK
    );

}
