package com.brucexu.springBootBackend.repository.projections;

public interface WorkExperienceVectorSearchProjection {
    Long getContentId();
    Double getDistance();

    Long getWorkExperienceId();
    Long getPersonalId();
    String getContent();
    String getCompanyName();
}
