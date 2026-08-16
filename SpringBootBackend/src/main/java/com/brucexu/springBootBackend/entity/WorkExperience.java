package com.brucexu.springBootBackend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workExperienceId;

    //** Foreign key pointing back to Person entity
    @ManyToOne
    @JoinColumn(name = "personal_id", nullable = false)
    private Personal person;
    //Getters and Setters
    public Personal getPerson() {
        return person;
    }
    public void setPerson(Personal person) {
        this.person = person;
    }

    private String companyName;
    private String title;
    private String employmentType;
    private String startDate;
    private String endDate;

    @OneToMany(mappedBy = "workExperience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperienceContent> contents = new ArrayList<>();
    //Getter
    public List<WorkExperienceContent> getContents() {
        return contents;
    }
    //Adder
    public void addContent(WorkExperienceContent workExperienceContent) {
        contents.add(workExperienceContent);
        workExperienceContent.setWorkExperience(this);
    }
    //Remover
    public void removeContent(WorkExperienceContent workExperienceContent) {
        contents.remove(workExperienceContent);
        workExperienceContent.setWorkExperience(null);
    }

    // optional
    private String location;

    // No-arg constructor required by JPA
    public WorkExperience() {
    }

    // All-args constructor (optional)
    public WorkExperience(Long workExperienceId, String companyName, String title,
                          String employmentType, String start, String end,
                          List<String> contents, String location) {
        this.workExperienceId = workExperienceId;
        this.companyName = companyName;
        this.title = title;
        this.employmentType = employmentType;
        this.startDate = start;
        this.endDate = end;
        this.location = location;
    }

    // Getters and Setters

    public Long getWorkExperienceId() {
        return workExperienceId;
    }

    public void setWorkExperienceId(Long workExperienceId) {
        this.workExperienceId = workExperienceId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
