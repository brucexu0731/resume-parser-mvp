package com.brucexu.springBootBackend.specifications;

import com.brucexu.springBootBackend.dto.candidateFilter.CandidateFiltersDTO;
import com.brucexu.springBootBackend.entity.Personal;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Not needed for now, building custom SQL native queries are much easier,
 * so just leaving this here in case would need specifications in the future
 *
 * Specifications are only useful when building custom WHERE conditionals, and
 * they return all database fields of an entity which then needs to be projected to
 * a projection then converted to a DTO
 */
public class CandidateFilterSpecification {

    public static Specification<Personal> getCandidateFilterSpecification(CandidateFiltersDTO filters) {
        return new Specification<Personal>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Personal> root,
                                                   CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
    }
}
