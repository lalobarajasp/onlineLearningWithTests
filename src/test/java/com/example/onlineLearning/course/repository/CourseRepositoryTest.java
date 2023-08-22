package com.example.onlineLearning.course.repository;

import com.example.onlineLearning.course.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

//configures the test to use Spring Data JPA and sets up an H2 in-memory database.
@DataJpaTest
//ensures that the configured test database (in this case, H2) is used and doesn't get replaced with an embedded database.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepositoryTest {

    //@Autowired with a real repository instance and TestEntityManager is generally more appropriate
    //when you want to test the integration between your repository, the JPA framework, and the database. (Integration Testing)
    //@Mock is used to create mock objects for the purpose of isolating the unit of code you are testing from its dependencies (Unit Testing)
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CourseRepository courseRepository;
    private Course course1;
    private Course course2;

    @BeforeEach
    public void setUp() {
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

        entityManager.persistAndFlush(course1);
        entityManager.persistAndFlush(course2);

    }

    @Test
    public void testFindByTitleContainingIgnoreCase() {
        List<Course> foundCourses =  courseRepository.findByTitleContainingIgnoreCase("Java");
        assertEquals(1, foundCourses.size());
        assertEquals(course1.getTitle(), foundCourses.get(0).getTitle());
    }

    @Test
    public void testFindByCategoryContainingIgnoreCase() {
        List<Course> foundCourses = courseRepository.findByCategoryContainingIgnoreCase("Design");
        assertEquals(1, foundCourses.size());
        assertEquals(course2.getCategory(), foundCourses.get(0).getCategory());

    }

    @Test
    public void testFindByKeywordsContainingIgnoreCase() {
        List<Course> foundCourses = courseRepository.findByKeywordsContainingIgnoreCase("Backend");
        assertEquals(1, foundCourses.size());
        assertEquals(course1.getKeywords(), foundCourses.get(0).getKeywords());
    }




}
