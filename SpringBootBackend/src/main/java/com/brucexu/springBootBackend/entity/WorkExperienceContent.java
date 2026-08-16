package com.brucexu.springBootBackend.entity;

import jakarta.persistence.*;

@Entity
public class WorkExperienceContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "work_experience_id", nullable = false)
    private WorkExperience workExperience;
    //Getters and Setters
    public WorkExperience getWorkExperience() {
        return workExperience;
    }
    public void setWorkExperience(WorkExperience workExperience) {
        this.workExperience = workExperience;
    }

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer contentIndex;

    // mapped to pgvector VECTOR(1536)
    @Column(columnDefinition = "vector(1536)")
    private float[] embedding;

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getContentIndex() {
        return contentIndex;
    }

    public void setContentIndex(Integer contentIndex) {
        this.contentIndex = contentIndex;
    }

    public float[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(float[] embedding) {
        this.embedding = embedding;
    }

}
