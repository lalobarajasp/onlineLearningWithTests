package com.example.onlineLearning.enrollment.service;

import com.example.onlineLearning.course.repository.CourseRepository;
import com.example.onlineLearning.enrollment.model.Enrollment;
import com.example.onlineLearning.enrollment.model.Status;
import com.example.onlineLearning.enrollment.repository.EnrollmentRepository;
import com.example.onlineLearning.user.appUser.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private CourseRepository courseRepository;

    //Enroll course
    public Enrollment enrollCourse(Enrollment enrollment) {

        if((appUserRepository.existsById(enrollment.getUser_id())) && (courseRepository.existsById(enrollment.getCourse_id()))){
            enrollment.setStatus(Status.ENROLL);
            enrollmentRepository.save(enrollment);
        }else{
            return null;
        }
        return enrollment;
    }

    //Unenroll course
    public Enrollment unEnrollCourse(Long id) {
        Enrollment enrollment = null;
        if(enrollmentRepository.existsById(id)){
            enrollment = enrollmentRepository.findById(id).orElseThrow();
            enrollment.setStatus(Status.UNENROLL);
            enrollmentRepository.save(enrollment);
        }else{
            return null;
        }
        return enrollment;
    }



}
