package com.example.onlineLearning.learningPath.model;

import jakarta.persistence.*;

@Entity
@Table(name = "learningPath")
public class LearningPath {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //required
    private String typePath; //custom or just a course

    public LearningPath() {
    }

    public LearningPath(Long id, String typePath) {
        this.id = id;
        this.typePath = typePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypePath() {
        return typePath;
    }

    public void setTypePath(String typePath) {
        this.typePath = typePath;
    }

    @Override
    public String toString() {
        return "LearningPath{" +
                "id=" + id +
                ", typePath='" + typePath + '\'' +
                '}';
    }
}
