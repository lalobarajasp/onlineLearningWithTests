package com.example.onlineLearning.enrollment.controller;

import com.example.onlineLearning.enrollment.model.Enrollment;
import com.example.onlineLearning.enrollment.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/enrollment")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping(path = "/enroll")
    private ResponseEntity<String> enrollCourseController(@RequestBody Enrollment enrollment) {

        Enrollment enrolled = enrollmentService.enrollCourse(enrollment);

        if (enrolled != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Enroll");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/unenroll/{id}")
    private String unEnrollController(@PathVariable("id") Long id){
        enrollmentService.unEnrollCourse(id);
        return "Unenroll";
    }


}
