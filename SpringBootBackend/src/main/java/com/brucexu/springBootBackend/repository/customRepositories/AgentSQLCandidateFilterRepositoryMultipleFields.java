package com.brucexu.springBootBackend.repository.customRepositories;

import com.brucexu.springBootBackend.dto.candidateFilter.CandidateFiltersDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RAGCandidateFilterRepositoryMultipleFields {

    @Autowired
    private EntityManager entityManager;

    public List<Long> filter(
            CandidateFiltersDTO filters
    ) {
        StringBuilder sql = new StringBuilder("""
                SELECT DISTINCT
                        p.personal_id AS personalId
                    FROM personal p
                    JOIN work_experience we
                        ON we.personal_id = p.personal_id
                    JOIN work_experience_content wec
                        ON wec.work_experience_id = we.work_experience_id
                    WHERE p.active = true
                """
        );

        Map<String, Object> parameters = new HashMap<>();

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
            if ("current".equalsIgnoreCase(filters.companyStatus())) {
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
            if ("current".equalsIgnoreCase(filters.titleStatus())) {
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


        sql.append("""
                 ORDER BY p.personal_id
                """);

        Query query = entityManager.createNativeQuery(sql.toString());
        parameters.forEach(query::setParameter);

        List<Long> res = query.getResultList();

        return res;
    }
}
