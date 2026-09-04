package com.brucexu.springBootBackend.repository.projections;

public interface SimplePersonProfileProjection {

    String getName();
    String getPhoneNumber();
    String getEmail();

    String[] getSkills();
    String getIndustry();
    Boolean getNeedVisa();
    String getAddDate();
    String getAddBy();
    String getUpdateDate();
    String getUpdateBy();
    String getCandidateSource();
    String getPreferredLocation();
    String getPreferredIndustry();
    String getPreferredBaseSalary();
    String getPreferredAnnualPackage();
    String getNoticePeriod();
    String getAdditionalInfo();
    String getMotivation();

    String getWorkExperiences();
    String getEducations();
}
