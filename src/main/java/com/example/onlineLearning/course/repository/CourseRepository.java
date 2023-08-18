package com.example.onlineLearning.course.repository;

import com.example.onlineLearning.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTitleContainingIgnoreCase(String title);
    List<Course> findByCategoryContainingIgnoreCase(String category);
    List<Course> findByKeywordsContainingIgnoreCase(String keywords);

}
