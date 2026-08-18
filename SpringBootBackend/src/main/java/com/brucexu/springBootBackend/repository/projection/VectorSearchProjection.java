package com.brucexu.springBootBackend.repository.projection;

public interface VectorSearchProjection {
    Long getContentId();
    String getContent();
    Double getDistance();

    Long getWorkExperienceId();
    String getCompanyName();
    String getTitle();
    String getEndDate();
    String getEmploymentType();

    Long getPersonalId();
    String getPersonName();
    Boolean getIsActive();
    String getIndustry();
    String getLatestCompany();
}
