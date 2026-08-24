package com.brucexu.springBootBackend.repository.projection;

public interface WorkExperienceVectorSearchProjection {
    Long getContentId();
    Double getDistance();

    Long getWorkExperienceId();
    Long getPersonalId();
    Boolean getIsActive();
}
