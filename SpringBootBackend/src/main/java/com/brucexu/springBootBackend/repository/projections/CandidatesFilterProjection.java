package com.brucexu.springBootBackend.repository.projections;

/**
 * Not used for now since I'm building custom repositories, projections
 * are only needed with jpaRepository methods
 */
public interface CandidatesFilterProjection {
    Long getPersonalId();
    String getName();
    String getPhoneNumber();
    String getEmail();
    String getLatestCompany();
    String getLatestRole();
    String getIsCurrent();

    String getIndustry();
    String getAddDate();
    String getUpdateDate();
}
