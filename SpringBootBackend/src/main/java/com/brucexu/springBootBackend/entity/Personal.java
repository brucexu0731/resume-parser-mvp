package com.brucexu.springBootBackend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Personal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personalId;

    //** Foreign key referencing Education entities
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();
    //Getter
    public List<Education> getEducations() {
        return educations;
    }
    //Adder
    public void addEducation(Education education) {
        educations.add(education);
        education.setPerson(this);
    }
    //Remover
    public void removeEducation(Education education) {
        educations.remove(education);
        education.setPerson(null);
    }

    //** Foreign key referencing WorkExperience entities
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperience> workExperiences = new ArrayList<>();
    //Getter
    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }
    //Adder
    public void addWorkExperience(WorkExperience workExperience) {
        workExperiences.add(workExperience);
        workExperience.setPerson(this);
    }
    //Remover
    public void removeWorkExperience(WorkExperience workExperience) {
        workExperiences.remove(workExperience);
        workExperience.setPerson(null);
    }



    private String name;
    private String phoneNumber;
    private String email;
    private String latestCompany;
    private String latestRole;
    private String isCurrent;
    private String highestDegree;
    private String highestDegreeSchool;
    private String highestDegreeGraduationDate;

    @ElementCollection
    private List<String> highestDegreeMajor;

    @ElementCollection
    private List<String> skills;

    // Optional personal
    private String age;
    private String sex;
    private String gender;

    // Optional recruiting info
    private Boolean active;
    private String industry; // Renamed to lowerCamelCase standard
    private Boolean needVisa;
    private String addDate;
    private String addBy;
    private String updateDate;
    private String updateBy;
    private String candidateSource;
    private String preferredLocation;
    private String preferredIndustry;
    private String preferredBaseSalary;
    private String preferredAnnualPackage;
    private String noticePeriod;

    // Future additions
    private String additionalInfo;
    private String motivation;

    // Default No-Arg Constructor (Required by JPA)
    public Personal() {
    }

    // All-Args Constructor
    public Personal(Long personalId, String name, String phoneNumber, String email,
                    String latestCompany, String latestRole, String isCurrent,
                    String highestDegree, String highestDegreeSchool, String highestDegreeGraduationDate,
                    List<String> highestDegreeMajor, List<String> skills, String age,
                    String sex, String gender, String industry, Boolean needVisa,
                    String addDate, String addBy, String updateDate, String updateBy,
                    String candidateSource, String preferredLocation, String preferredIndustry,
                    String preferredBaseSalary, String preferredAnnualPackage, String noticePeriod,
                    String additionalInfo, String motivation, Boolean active) {
        this.personalId = personalId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.latestCompany = latestCompany;
        this.latestRole = latestRole;
        this.isCurrent = isCurrent;
        this.highestDegree = highestDegree;
        this.highestDegreeSchool = highestDegreeSchool;
        this.highestDegreeGraduationDate = highestDegreeGraduationDate;
        this.highestDegreeMajor = highestDegreeMajor;
        this.skills = skills;
        this.age = age;
        this.sex = sex;
        this.gender = gender;
        this.industry = industry;
        this.needVisa = needVisa;
        this.addDate = addDate;
        this.addBy = addBy;
        this.updateDate = updateDate;
        this.updateBy = updateBy;
        this.candidateSource = candidateSource;
        this.preferredLocation = preferredLocation;
        this.preferredIndustry = preferredIndustry;
        this.preferredBaseSalary = preferredBaseSalary;
        this.preferredAnnualPackage = preferredAnnualPackage;
        this.noticePeriod = noticePeriod;
        this.additionalInfo = additionalInfo;
        this.motivation = motivation;
        this.active = active;
    }

    // Getters and Setters

    public Long getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Long personalId) {
        this.personalId = personalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLatestCompany() {
        return latestCompany;
    }

    public void setLatestCompany(String latestCompany) {
        this.latestCompany = latestCompany;
    }

    public String getLatestRole() {
        return latestRole;
    }

    public void setLatestRole(String latestRole) {
        this.latestRole = latestRole;
    }

    public String getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getHighestDegree() {
        return highestDegree;
    }

    public void setHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
    }

    public String getHighestDegreeSchool() {
        return highestDegreeSchool;
    }

    public void setHighestDegreeSchool(String highestDegreeSchool) {
        this.highestDegreeSchool = highestDegreeSchool;
    }

    public String getHighestDegreeGraduationDate() {
        return highestDegreeGraduationDate;
    }

    public void setHighestDegreeGraduationDate(String highestDegreeGraduationDate) {
        this.highestDegreeGraduationDate = highestDegreeGraduationDate;
    }

    public List<String> getHighestDegreeMajor() {
        return highestDegreeMajor;
    }

    public void setHighestDegreeMajor(List<String> highestDegreeMajor) {
        this.highestDegreeMajor = highestDegreeMajor;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Boolean getNeedVisa() {
        return needVisa;
    }

    public void setNeedVisa(Boolean needVisa) {
        this.needVisa = needVisa;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCandidateSource() {
        return candidateSource;
    }

    public void setCandidateSource(String candidateSource) {
        this.candidateSource = candidateSource;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public String getPreferredIndustry() {
        return preferredIndustry;
    }

    public void setPreferredIndustry(String preferredIndustry) {
        this.preferredIndustry = preferredIndustry;
    }

    public String getPreferredBaseSalary() {
        return preferredBaseSalary;
    }

    public void setPreferredBaseSalary(String preferredBaseSalary) {
        this.preferredBaseSalary = preferredBaseSalary;
    }

    public String getPreferredAnnualPackage() {
        return preferredAnnualPackage;
    }

    public void setPreferredAnnualPackage(String preferredAnnualPackage) {
        this.preferredAnnualPackage = preferredAnnualPackage;
    }

    public String getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(String noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive (boolean active) {
        this.active = active;
    }

}
