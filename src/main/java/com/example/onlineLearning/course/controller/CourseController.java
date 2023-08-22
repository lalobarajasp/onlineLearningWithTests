package com.example.onlineLearning.course.controller;

import com.example.onlineLearning.course.model.Course;
import com.example.onlineLearning.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping(path = "/create")
    private ResponseEntity<String> createController(@Valid @RequestBody Course course){
        Course temporal = courseService.create(course);
        try{
            return ResponseEntity.ok("Course is valid");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
    }

    //Response errors and which files are mandatory to fill
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @GetMapping(path = "/courses")
    private ResponseEntity<List<Course>> getAllCoursesController (){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping (path = "/courses/{courseId}")
    private Course getOnlyCourseController (@PathVariable("courseId")Long course_id) {
        return courseService.getOnlyCourse(course_id);
    }

    @PutMapping(path = "/courses/{courseId}")
    public Course updateCourseController(@PathVariable("courseId") Long course_id, @RequestBody Course updatedCourse) {
        return courseService.updateCourse(course_id, updatedCourse);
    }

    @DeleteMapping (path = "/courses/{courseId}")
    public Course deleteCourseController (@PathVariable("courseId") Long course_id) {
        return courseService.deleteCourse(course_id);
    }

    @GetMapping(path = "/search")
    public List<Course> searchCourses(@RequestParam("search") String searchTerm) {
        return courseService.searchCourses(searchTerm);
    }



}
