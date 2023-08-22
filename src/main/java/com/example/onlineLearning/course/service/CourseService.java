package com.example.onlineLearning.course.service;

import com.example.onlineLearning.course.model.Course;
import com.example.onlineLearning.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    //Create Course

    public Course create(Course course) {
        return courseRepository.save(course);
    }

    //Retrieve all Courses

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    //Retrieve Only a Course

    public Course getOnlyCourse(Long course_id) {
        return courseRepository.findById(course_id).orElseThrow(
                () -> new IllegalStateException("The course with id: " + course_id + " doesn't exist.")
        );
    }

    //Update Course

    public Course updateCourse(Long course_id, Course updatedCourse) {
        Course existingCourse = null;
        if(courseRepository.existsById(course_id)) {
            existingCourse = courseRepository.findById(course_id).get();
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setCategory(updatedCourse.getCategory());
            existingCourse.setKeywords(updatedCourse.getKeywords());
            existingCourse.setStartDate(updatedCourse.getStartDate());
            existingCourse.setEndDate(updatedCourse.getEndDate());
            existingCourse.setDurationInWeeks(updatedCourse.getDurationInWeeks());
            courseRepository.save(existingCourse);
        }else {
            System.out.println("Update | The course with the id: " + course_id + " doesn't exist.");
        }
        return existingCourse;
    }


    //Delete Course
    public Course deleteCourse(Long course_id) {
        Course tmpCourse = null;
        if (courseRepository.existsById(course_id)) {
            tmpCourse = courseRepository.findById(course_id).get();
            courseRepository.deleteById(course_id);
        }
        return tmpCourse;
    }

    //Search Course by title, category and keywords
    public List<Course> searchCourses(String search) {
        List<Course> coursesByTitle = courseRepository.findByTitleContainingIgnoreCase(search);
        List<Course> coursesByCategory = courseRepository.findByCategoryContainingIgnoreCase(search);
        List<Course> coursesByKeyword = courseRepository.findByKeywordsContainingIgnoreCase(search);

        List<Course> searchResults = new ArrayList<>();
        searchResults.addAll(coursesByTitle);
        searchResults.addAll(coursesByCategory);
        searchResults.addAll(coursesByKeyword);

        return searchResults;
    }




}
