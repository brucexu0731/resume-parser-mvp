package com.brucexu.springBootBackend.repository.customRepositories;

import com.brucexu.springBootBackend.dto.candidateFilter.SimpleCandidateFiltersDTO;
import com.brucexu.springBootBackend.dto.candidateFilter.FilterResultDTO;
import com.brucexu.springBootBackend.services.EmbeddingService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SimpleCandidateFilterRepositoryWithVector {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EmbeddingService embeddingService;

    public List<FilterResultDTO> filter(
            SimpleCandidateFiltersDTO filters
    ) {
        StringBuilder sql = new StringBuilder("""
            SELECT DISTINCT
                    p.personal_id AS personalId,
                    p.name AS name,
                    p.phone_number AS phoneNumber,
                    p.email AS email,
                    p.latest_company AS latestCompany,
                    p.latest_role AS latestRole,
                    p.is_current AS isCurrent,
                    p.industry AS industry,
                    p.add_date AS addDate,
                    p.update_date AS updateDate
            """);

        Map<String, Object> parameters = new HashMap<>();
        boolean hasVectorQuery = (filters.vectorQuery() != null && !filters.vectorQuery().isEmpty());

        if (hasVectorQuery) {
            sql.append("""
                , MIN(wec.embedding <=> CAST(:vector AS vector)) AS distance
            """);

            String vectorString = embeddingService.toVectorString(
                    embeddingService.embed(filters.vectorQuery().strip().toLowerCase())
            );
            parameters.put(
                    "vector", vectorString
            );
        }

        sql.append("""
                FROM personal p
                JOIN work_experience we
                    ON we.personal_id = p.personal_id
                JOIN work_experience_content wec
                    ON wec.work_experience_id = we.work_experience_id
                WHERE p.active = true
                """
        );

        if (hasVectorQuery) {
            sql.append(" AND (wec.embedding <=> CAST(:vector AS vector)) < 0.65");
        }

        if (filters.name() != null) {
            sql.append(" AND LOWER(p.name) LIKE :candidateName");
            parameters.put(
                    "candidateName",
                    "%" + filters.name().toLowerCase() + "%"
            );
        }
        if (filters.phoneNumber() != null) {
            sql.append(" AND p.phone_number = :phoneNumber");
            parameters.put("phoneNumber", filters.phoneNumber());
        }
        if (filters.email() != null) {
            sql.append(" AND p.email = :email");
            parameters.put("email", filters.email());
        }
        if (filters.highestDegree() != null) {
            sql.append(" AND LOWER(p.highest_degree) = :highestDegree");
            parameters.put("highestDegree", filters.highestDegree().toLowerCase());
        }
        if (filters.industry() != null) {
            sql.append(" AND LOWER(p.industry) = :industry");
            parameters.put("industry", filters.industry().toLowerCase());
        }
        if (filters.addBy() != null) {
            sql.append(" AND LOWER(p.add_by) = :addBy");
            parameters.put("addBy", filters.addBy().toLowerCase());
        }


        if (filters.company() != null) {
            if ("current".equalsIgnoreCase(filters.companyStatus())){
                sql.append("""
                         AND EXISTS(
                            SELECT 1
                            FROM work_experience we
                            WHERE we.personal_id = p.personal_id
                                AND LOWER(we.end_date) = 'present'
                                AND LOWER(we.company_name) LIKE :companyName
                        )
                        """);
            } else {
                sql.append(" AND LOWER(we.company_name) LIKE :companyName");
            }

            parameters.put(
                    "companyName",
                    "%" + filters.company().toLowerCase() + "%"
            );
        }

        if (filters.title() != null) {
            if ("current".equalsIgnoreCase(filters.titleStatus())){
                sql.append("""
                         AND EXISTS(
                            SELECT 1
                            FROM work_experience we
                            WHERE we.personal_id = p.personal_id
                                AND LOWER(we.end_date) = 'present'
                                AND LOWER(we.title) LIKE :titleName
                        )
                        """);
            } else {
                sql.append(" AND LOWER(we.title) LIKE :titleName");
            }

            parameters.put(
                    "titleName",
                    "%" + filters.title().toLowerCase() + "%"
            );
        }

        if (hasVectorQuery){
            sql.append("""
                 GROUP BY
                    p.personal_id,
                    p.name,
                    p.phone_number,
                    p.email,
                    p.latest_company,
                    p.latest_role,
                    p.is_current,
                    p.industry,
                    p.add_date,
                    p.update_date
            
                ORDER BY distance
            """);
        } else {
            sql.append("""
              ORDER BY name
             """);
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        parameters.forEach(query::setParameter);

        List<Object[]> rows = query.getResultList();

        return rows.stream()
                .map(row -> new FilterResultDTO(
                        ((Number) row[0]).longValue(), // personalId
                        (String) row[1],               // name
                        (String) row[2],               // phoneNumber
                        (String) row[3],               // email
                        (String) row[4],               // latestCompany
                        (String) row[5],               // latestRole
                        (String) row[6],               // isCurrent
                        (String) row[7],               // industry
                        (String)row[8],                // addDate
                        (String)row[9]                 // updateDate
                ))
                .toList();
    }
}