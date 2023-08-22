package com.example.onlineLearning.course.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.Test;

class CourseTest {

    @Test
    public void testDefaultConstructor() {
        Course course = new Course();
        //Check default values
        assertEquals(null, course.getCourse_id());
        assertEquals(null, course.getTitle());
        assertEquals(null, course.getCategory());
        assertEquals(null, course.getKeywords());
        assertEquals(null, course.getStartDate());
        assertEquals(null, course.getEndDate());
        assertEquals(null, course.getDurationInWeeks());
    }

    @Test
    public void testParameterizedConstructor() {
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

        Course course = new Course(title,category,keywords,startDate,endDate,durationInWeeks);

        assertEquals(title, course.getTitle());
        assertEquals(category, course.getCategory());
        assertEquals(keywords, course.getKeywords());
        assertEquals(startDate, course.getStartDate());
        assertEquals(endDate, course.getEndDate());
        assertEquals(durationInWeeks, course.getDurationInWeeks());
    }

    @Test
    public  void testGettersAndSetters() {
        Course course = new Course();
        //Set values using setters
        Long id = 1L;
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

        course.setCourse_id(id);
        course.setTitle(title);
        course.setCategory(category);
        course.setKeywords(keywords);
        course.setStartDate(startDate);
        course.setEndDate(endDate);
        course.setDurationInWeeks(durationInWeeks);

        assertEquals(id, course.getCourse_id());
        assertEquals(title, course.getTitle());
        assertEquals(category, course.getCategory());
        assertEquals(keywords, course.getKeywords());
        assertEquals(startDate, course.getStartDate());
        assertEquals(endDate, course.getEndDate());
        assertEquals(durationInWeeks, course.getDurationInWeeks());

    }



}

