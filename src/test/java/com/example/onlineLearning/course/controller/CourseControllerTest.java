package com.example.onlineLearning.course.controller;

import com.example.onlineLearning.course.model.Course;
import com.example.onlineLearning.course.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService courseService;
    private Course validCourse;
    private Course validCourse2;


    @BeforeEach
    public void setUp() {
        String title = "Java";
        String category = "Development";
        String keywords = "Backend";
        Long durationInWeeks = 4L;

        validCourse = new Course(title, category, keywords, null, null, durationInWeeks);

        validCourse2 = new Course("Python", "Development", "Backend", null, null, 4L);

    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testCreateValidCourse() throws Exception {
        // Mock the behavior of the service
        when(courseService.create(Mockito.any(Course.class))).thenReturn(validCourse);

        // Convert the Course object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String courseJson = objectMapper.writeValueAsString(validCourse);

        MvcResult result = mockMvc.perform(post("/course/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson))
                .andExpect(status().isOk())
                .andReturn();

        // Get the response content as a String
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        // Perform assertions on the response content
        assertEquals("Course is valid", responseContent);
    }

    @Test
    public void testCreateController_InvalidCourse_ReturnsBadRequest() throws Exception {
        Course invalidCourse = new Course();

        BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
        List<FieldError> errors = new ArrayList<>();
        errors.add(new FieldError("course", "fieldName", "Field error message"));
        Mockito.when(mockBindingResult.getFieldErrors()).thenReturn(errors);

        mockMvc.perform(post("/course/create")
                        .content(asJsonString(invalidCourse))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Helper method to convert objects to JSON format
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testHandleValidationExceptions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/course/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testGetAllCoursesController() throws Exception {
        // Mock the behavior of the service to return a list of courses
        List<Course> courses = new ArrayList<>();
        courses.add(validCourse);
        courses.add(validCourse2);

        when(courseService.getAllCourses()).thenReturn(courses);

        MvcResult result = mockMvc.perform(get("/course/courses"))
                .andExpect(status().isOk())
                .andReturn();

        // Get the response content as a String
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        String expectedJson = "["
                + "{\"title\":\"Java\",\"category\":\"Development\",\"keywords\":\"Backend\",\"startDate\":null,\"endDate\":null,\"durationInWeeks\":4},"
                + "{\"title\":\"Python\",\"category\":\"Development\",\"keywords\":\"Backend\",\"startDate\":null,\"endDate\":null,\"durationInWeeks\":4}"
                + "]";

        // Debugging output
        System.out.println("Expected JSON: " + expectedJson);

        // Use JSONAssert to compare the expected JSON with the actual response JSON
        try {
            JSONAssert.assertEquals(expectedJson, responseContent, false);
        } catch (AssertionError e) {
            System.err.println("JSON Assertion Error: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testGetOnlyCourseController() throws Exception {
        Long courseId = 1L;
        validCourse.setCourse_id(courseId);

        // Mock the behavior of the service to return the sample course
        when(courseService.getOnlyCourse(courseId)).thenReturn(validCourse);

        // Perform the GET request
        mockMvc.perform(get("/course/courses/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.course_id").value(courseId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.keywords").value("Backend"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.durationInWeeks").value(4));
    }




    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testUpdateCourseController() throws Exception {
        // Create a sample updated course
        Course updatedCourse = new Course("Updated Java", "Development", "Backend", null, null, 6L);

        // Convert the updated course to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String updatedCourseJson = objectMapper.writeValueAsString(updatedCourse);

        // Mock the behavior of the service
        when(courseService.updateCourse(Mockito.anyLong(), Mockito.any(Course.class))).thenReturn(updatedCourse);

        // Perform the PUT request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/course/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCourseJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Get the response content as a String
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        // Convert the response JSON to a Course object for further assertion
        Course responseCourse = objectMapper.readValue(responseContent, Course.class);

        // Perform assertions on the response content
        assertEquals("Updated Java", responseCourse.getTitle());
        assertEquals("Development", responseCourse.getCategory());
        assertEquals("Backend", responseCourse.getKeywords());
        assertNull(responseCourse.getStartDate());
        assertNull(responseCourse.getEndDate());
        assertEquals(6L, responseCourse.getDurationInWeeks());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteCourseController() throws Exception {
        // Create a sample course ID for deletion
        Long courseIdToDelete = 1L;

        // Create a sample deleted course
        Course deletedCourse = new Course("Deleted Course", "Development", "Backend", null, null, 8L);

        // Mock the behavior of the service
        when(courseService.deleteCourse(Mockito.anyLong())).thenReturn(deletedCourse);

        // Perform the DELETE request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/course/courses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Get the response content as a String
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        // Convert the response JSON to a Course object for further assertion
        ObjectMapper objectMapper = new ObjectMapper();
        Course responseCourse = objectMapper.readValue(responseContent, Course.class);

        // Perform assertions on the response content
        assertEquals("Deleted Course", responseCourse.getTitle());
        assertEquals("Development", responseCourse.getCategory());
        assertEquals("Backend", responseCourse.getKeywords());
        assertNull(responseCourse.getStartDate());
        assertNull(responseCourse.getEndDate());
        assertEquals(8L, responseCourse.getDurationInWeeks());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testSearchCourses() throws Exception {
        // Create a sample search term
        String searchTerm = "Java";

        // Create a list of sample courses matching the search term
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Java Programming", "Development", "Programming", null, null, 6L));
        courses.add(new Course("Advanced Java", "Development", "Programming", null, null, 4L));

        // Mock the behavior of the service
        when(courseService.searchCourses(Mockito.anyString())).thenReturn(courses);

        // Perform the GET request with the search term
        MvcResult result = mockMvc.perform(get("/course/search")
                        .param("search", searchTerm))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Get the response content as a String
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        // Convert the response JSON to a List of Course objects for further assertion
        ObjectMapper objectMapper = new ObjectMapper();
        List<Course> responseCourses = objectMapper.readValue(
                responseContent, objectMapper.getTypeFactory().constructCollectionType(List.class, Course.class));

        // Perform assertions on the response content
        assertEquals(2, responseCourses.size());
        assertEquals("Java Programming", responseCourses.get(0).getTitle());
        assertEquals("Advanced Java", responseCourses.get(1).getTitle());
        // Add more assertions as needed for other properties

        // Verify that the service method was called with the correct search term
        verify(courseService, times(1)).searchCourses(searchTerm);
    }


























}
