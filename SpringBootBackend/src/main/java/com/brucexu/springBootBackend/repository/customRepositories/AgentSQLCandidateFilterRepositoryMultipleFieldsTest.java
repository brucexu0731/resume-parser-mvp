package com.brucexu.springBootBackend.repository.customRepositories;

import com.brucexu.springBootBackend.dto.candidateFilter.AgentCandidateFiltersDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AgentSQLCandidateFilterRepositoryMultipleFieldsTest {

    @Autowired
    private EntityManager entityManager;

    public List<String> filter(
            AgentCandidateFiltersDTO filters
    ) {
        StringBuilder sql = new StringBuilder("""
                SELECT DISTINCT
                        p.name
                    FROM personal p
                    JOIN work_experience we
                        ON we.personal_id = p.personal_id
                    JOIN work_experience_content wec
                        ON wec.work_experience_id = we.work_experience_id
                    WHERE p.active = true
                """
        );

        Map<String, Object> parameters = new HashMap<>();

        // IDs
        if (filters.ids() != null && !filters.ids().isEmpty()) {
            sql.append(" AND p.personal_id IN (:ids)");
            parameters.put("ids", filters.ids());
        }

        // NAME
        if (filters.name() != null && !filters.name().isEmpty()) {
            sql.append(" AND (");

            for (int i = 0; i < filters.name().size(); i++) {
                if (i > 0) {
                    sql.append(" OR ");
                }

                String param = "candidateName" + i;

                sql.append("LOWER(p.name) LIKE :").append(param);

                parameters.put(
                        param,
                        "%" + filters.name().get(i).toLowerCase() + "%"
                );
            }

            sql.append(")");
        }

        // PHONE NUMBER
        if (filters.phoneNumber() != null && !filters.phoneNumber().isEmpty()) {
            sql.append(" AND p.phone_number IN (:phoneNumbers)");
            parameters.put("phoneNumbers", filters.phoneNumber());
        }

        // EMAIL
        if (filters.email() != null && !filters.email().isEmpty()) {
            sql.append(" AND p.email IN (:emails)");
            parameters.put("emails", filters.email());
        }

        // HIGHEST DEGREE
        if (filters.highestDegree() != null && !filters.highestDegree().isEmpty()) {
            sql.append(" AND LOWER(p.highest_degree) IN (:highestDegrees)");

            parameters.put(
                    "highestDegrees",
                    filters.highestDegree()
                            .stream()
                            .map(String::toLowerCase)
                            .toList()
            );
        }

        // INDUSTRY
        if (filters.industry() != null && !filters.industry().isEmpty()) {
            sql.append(" AND LOWER(p.industry) IN (:industries)");

            parameters.put(
                    "industries",
                    filters.industry()
                            .stream()
                            .map(String::toLowerCase)
                            .toList()
            );
        }

        // ADD BY
        if (filters.addBy() != null && !filters.addBy().isEmpty()) {
            sql.append(" AND LOWER(p.add_by) IN (:addBy)");

            parameters.put(
                    "addBy",
                    filters.addBy()
                            .stream()
                            .map(String::toLowerCase)
                            .toList()
            );
        }

        // COMPANY
        if (filters.company() != null && !filters.company().isEmpty()) {

            if ("current".equalsIgnoreCase(filters.companyStatus())) {

                sql.append("""
                    AND EXISTS (
                        SELECT 1
                        FROM work_experience current_we
                        WHERE current_we.personal_id = p.personal_id
                            AND LOWER(current_we.end_date) = 'present'
                            AND (
                    """);

                addLikeConditions(
                        sql,
                        parameters,
                        filters.company(),
                        "current_we.company_name",
                        "company"
                );

                sql.append("""
                            )
                    )
                    """);

            } else {

                sql.append(" AND (");

                addLikeConditions(
                        sql,
                        parameters,
                        filters.company(),
                        "we.company_name",
                        "company"
                );

                sql.append(")");
            }
        }

        // TITLE
        if (filters.title() != null && !filters.title().isEmpty()) {

            if ("current".equalsIgnoreCase(filters.titleStatus())) {

                sql.append("""
                    AND EXISTS (
                        SELECT 1
                        FROM work_experience current_we
                        WHERE current_we.personal_id = p.personal_id
                            AND LOWER(current_we.end_date) = 'present'
                            AND (
                    """);

                addLikeConditions(
                        sql,
                        parameters,
                        filters.title(),
                        "current_we.title",
                        "title"
                );

                sql.append("""
                            )
                    )
                    """);

            } else {

                sql.append(" AND (");

                addLikeConditions(
                        sql,
                        parameters,
                        filters.title(),
                        "we.title",
                        "title"
                );

                sql.append(")");
            }
        }

        sql.append("""
                 ORDER BY p.name
                """);

        Query query = entityManager.createNativeQuery(sql.toString());
        parameters.forEach(query::setParameter);

        List<String> res = query.getResultList();

        return res;
    }

    private void addLikeConditions(
            StringBuilder sql,
            Map<String, Object> parameters,
            List<String> values,
            String column,
            String parameterPrefix
    ) {

        for (int i = 0; i < values.size(); i++) {

            if (i > 0) {
                sql.append(" OR ");
            }

            String parameterName = parameterPrefix + i;

            sql.append("LOWER(")
                    .append(column)
                    .append(") LIKE :")
                    .append(parameterName);

            parameters.put(
                    parameterName,
                    "%" + values.get(i).toLowerCase() + "%"
            );
        }
    }
}
