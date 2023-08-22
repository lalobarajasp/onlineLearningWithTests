package com.example.onlineLearning.course.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long course_id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Category is mandatory")
    private String category;
    private String keywords;
    private Date startDate;
    private Date endDate;
    private Long durationInWeeks;


    public Course() {
    }

    public Course(String title, String category, String keywords, Date startDate, Date endDate, Long durationInWeeks) {
        this.title = title;
        this.category = category;
        this.keywords = keywords;
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationInWeeks = durationInWeeks;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getDurationInWeeks() {
        return durationInWeeks;
    }

    public void setDurationInWeeks(Long durationInWeeks) {
        this.durationInWeeks = durationInWeeks;
    }


}
