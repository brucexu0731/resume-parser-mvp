package com.brucexu.springBootBackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long educationId;

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


    private String schoolName;
    private String degree;
    private String major;
    private String graduationDate;

    // Optional fields
    private String grade;
    private List<String> honors;

    // No-arg constructor required by JPA
    public Education() {
    }

    // All-args constructor
    public Education(Long educationId, String schoolName, String degree,
                     String major, String graduationDate, String grade, List<String> honors) {
        this.educationId = educationId;
        this.schoolName = schoolName;
        this.degree = degree;
        this.major = major;
        this.graduationDate = graduationDate;
        this.grade = grade;
        this.honors = honors;
    }

    // Getters and Setters

    public Long getEducationId() {
        return educationId;
    }

    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<String> getHonors() {
        return honors;
    }

    public void setHonors(List<String> honors) {
        this.honors = honors;
    }
}