package com.brucexu.springBootBackend.repository;

import com.brucexu.springBootBackend.entity.WorkExperience;
import com.brucexu.springBootBackend.entity.WorkExperienceContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkExperienceContentRespository
        extends JpaRepository<WorkExperienceContent, Long> {

    @Query(value = """
        SELECT content
        FROM work_experience_content
        ORDER BY embedding <=> CAST(:embedding AS vector)
        LIMIT :limit
        """, nativeQuery = true)
    List<String> findSimilar(
            @Param("embedding") String embedding,
            @Param("limit") int limit
    );

}
