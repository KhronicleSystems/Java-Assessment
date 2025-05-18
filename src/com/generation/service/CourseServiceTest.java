package com.generation.service;

import com.generation.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {

    private CourseService courseService;
    private Student student;

    @BeforeEach
    void setUp() {
        courseService = new CourseService();
        student = new Student("S001", "John Doe", "john@example.com", null);
    }

    @Test
    void testGetCourse() {
        var course = courseService.getCourse("INTRO-CS-1");
        assertNotNull(course);
        assertEquals("Introduction to Computer Science", course.getName());

        assertNull(courseService.getCourse("NON-EXISTENT"));
    }

    @Test
    void testEnrollStudent() {
        assertTrue(courseService.enrollStudent("INTRO-CS-1", student));

        // Duplicate enrollment should fail
        assertFalse(courseService.enrollStudent("INTRO-CS-1", student));

        // Enroll to another course
        assertTrue(courseService.enrollStudent("INTRO-WEB-1", student));
    }

    @Test
    void testShowSummaryRuns() {
        courseService.enrollStudent("INTRO-CS-1", student);
        courseService.showSummary();
    }

    @Test
    void testShowEnrolledStudentsForEmptyCourse() {
        // Should run without exception even if no students enrolled
        courseService.showEnrolledStudents("INTRO-CS-3");
    }
}
