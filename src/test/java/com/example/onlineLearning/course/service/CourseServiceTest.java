package com.example.onlineLearning.course.service;

import com.example.onlineLearning.course.model.Course;
import com.example.onlineLearning.course.repository.CourseRepository;
import com.example.onlineLearning.user.appUser.model.AppUser;
import com.example.onlineLearning.user.appUser.model.AppUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private CourseService courseService;
    private Course course1;
    private Course course2;

    @BeforeEach
    public void setUp() {
        //Start Mockito
        MockitoAnnotations.initMocks(this);

        String title = "Java";
        String category = "Development";
        String keywords = "Backend";
        String startString = "11-12-2023";
        String endString = "12-12-2023";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(startString);
            endDate = format.parse(endString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Long durationInWeeks = 4L;

        String title2 = "UI";
        String category2 = "Design";
        String keywords2 = "Frontend";
        String startString2 = "10-12-2023";
        String endString2 = "11-12-2023";
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate2 = null;
        Date endDate2 = null;
        try {
            startDate = format.parse(startString2);
            endDate = format.parse(endString2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Long durationInWeeks2 = 4L;

        course1 = new Course(title,category,keywords,startDate,endDate,durationInWeeks);
        course2 = new Course(title2,category2,keywords2,startDate2,endDate2,durationInWeeks2);
    }

    @Test
    public void testCreateCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course1);
        Course createdCourse = courseService.create(course1);
        assertNotNull(createdCourse);
        verify(courseRepository, times(1)).save(course1);
    }

    @Test
    public void testGetOnlyCourseExisting() {
        course1.setCourse_id(1L);
        when(courseRepository.findById(course1.getCourse_id())).thenReturn(Optional.of(course1));
        Course retrievedCourse = courseService.getOnlyCourse(course1.getCourse_id());
        assertNotNull(retrievedCourse);
        verify(courseRepository, times(1)).findById(course1.getCourse_id());
    }

    @Test
    public void testGetOnlyCourseNonExisting() {
        course1.setCourse_id(1L);
        when(courseRepository.findById(course1.getCourse_id())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> courseService.getOnlyCourse(course1.getCourse_id()));
        verify(courseRepository, times(1)).findById(course1.getCourse_id());
    }

    @Test
    public  void testGetAllCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        when(courseRepository.findAll()).thenReturn(courses);
        List<Course> allCourses = courseService.getAllCourses();

        assertNotNull(allCourses);
        assertEquals(2, allCourses.size());

        assertEquals("Java", allCourses.get(0).getTitle());
        assertEquals("UI", allCourses.get(1).getTitle());

        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testUpdatedCourseExisting() {
        Long courseId = 1L;
        course1.setCourse_id(courseId);

        String startString = "01-12-2024";
        String endString = "02-12-2024";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(startString);
            endDate = format.parse(endString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Course updatedCourse = new Course("Q&A", "Testing", "RestAPI", startDate, endDate,4L);
        updatedCourse.setCourse_id(courseId);

        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course1));
        when(courseRepository.save(any(Course.class))).thenReturn(updatedCourse);

        Course result = courseService.updateCourse(courseId, updatedCourse);

        assertNotNull(result);
        assertEquals("Q&A", result.getTitle());
        assertEquals("Testing", result.getCategory());
        assertEquals("RestAPI", result.getKeywords());

        verify(courseRepository, times(1)).existsById(courseId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(course1);

    }

    @Test
    public  void testUpdatedCourseNonExisting() {
        Long courseId = 1L;

        String startString = "01-12-2024";
        String endString = "02-12-2024";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(startString);
            endDate = format.parse(endString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Course updatedCourse = new Course("Q&A", "Testing", "RestAPI", startDate, endDate,4L);
        updatedCourse.setCourse_id(courseId);

        when(courseRepository.existsById(courseId)).thenReturn(false);

        Course result = courseService.updateCourse(courseId, updatedCourse);

        assertNull(result);

        verify(courseRepository, times(1)).existsById(courseId);
        verify(courseRepository, never()).findById(courseId);
        verify(courseRepository, never()).save(any(Course.class));

    }

    @Test
    public void testDeletedCourseExisting() {
        course1.setCourse_id(1L);
        when(courseRepository.existsById(course1.getCourse_id())).thenReturn(true);
        when(courseRepository.findById(course1.getCourse_id())).thenReturn(Optional.of(course1));

        Course result = courseService.deleteCourse(course1.getCourse_id());

        assertNotNull(result);
        assertEquals(course1, result);

        verify(courseRepository, times(1)).existsById(course1.getCourse_id());
        verify(courseRepository, times(1)).findById(course1.getCourse_id());
        verify(courseRepository, times(1)).deleteById(course1.getCourse_id());
    }

    @Test
    public void testDeletedCourseNonExisting() {
        Long courseId = 1L;
        when(courseRepository.existsById(courseId)).thenReturn(false);
        Course result = courseService.deleteCourse(courseId);
        assertNull(result);

        verify(courseRepository, times(1)).existsById(courseId);
        verify(courseRepository, never()).findById(courseId);
        verify(courseRepository, never()).deleteById(courseId);
    }

    @Test
    public void testSearchCourses() {
        String searchQueryTitle = "Java";
        String searchQueryCategory = "Development";
        String searchQueryKeyword = "Backend";

        List<Course> courses = new ArrayList<>();
        courses.add(course1);

        when(courseRepository.findByTitleContainingIgnoreCase(searchQueryTitle)).thenReturn(courses);
        when(courseRepository.findByCategoryContainingIgnoreCase(searchQueryCategory)).thenReturn(courses);
        when(courseRepository.findByKeywordsContainingIgnoreCase(searchQueryKeyword)).thenReturn(courses);

        List<Course> searchTitle = courseService.searchCourses(searchQueryTitle);
        List<Course> searchCategory = courseService.searchCourses(searchQueryCategory);
        List<Course> searchKeyword = courseService.searchCourses(searchQueryKeyword);

        assertNotNull(searchTitle);
        assertNotNull(searchCategory);
        assertNotNull(searchKeyword);

        assertEquals(1, searchTitle.size());
        assertEquals(1, searchCategory.size());
        assertEquals(1, searchKeyword.size());

        verify(courseRepository, times(1)).findByTitleContainingIgnoreCase(searchQueryTitle);
        verify(courseRepository, times(1)).findByCategoryContainingIgnoreCase(searchQueryCategory);
        verify(courseRepository, times(1)).findByKeywordsContainingIgnoreCase(searchQueryKeyword);


}







}
