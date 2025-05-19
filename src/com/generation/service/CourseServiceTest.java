package com.generation.service;

import com.generation.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {

    private CourseService courseService;
    private Student student;

    /**
     * Sets up a fresh instance of CourseService and a sample Student before each test.
     */
    @BeforeEach
    void setUp() {
        courseService = new CourseService();
        student = new Student("S001", "John Doe", "john@example.com", null);
    }

    /**
     * Tests retrieving courses by their course code.
     * Verifies that known course codes return the expected course,
     * and unknown codes return null.
     */
    @Test
    void testGetCourse() {
        var course = courseService.getCourse("INTRO-CS-1");
        assertNotNull(course);
        assertEquals("Introduction to Computer Science", course.getName());

        assertNull(courseService.getCourse("NON-EXISTENT"));
    }

    /**
     * Tests enrolling a student to a course.
     * Verifies that enrolling a student succeeds the first time,
     * fails on duplicate enrollment,
     * and succeeds for a different course.
     */
    @Test
    void testEnrollStudent() {
        // First enrollment should succeed
        assertTrue(courseService.enrollStudent("INTRO-CS-1", student));

        // Duplicate enrollment to the same course should fail
        assertFalse(courseService.enrollStudent("INTRO-CS-1", student));

        // Enrollment to a different course should succeed
        assertTrue(courseService.enrollStudent("INTRO-WEB-1", student));
    }

    /**
     * Tests that the showSummary method runs without exceptions.
     * This method prints all courses and enrolled students.
     * No assertions here as it is primarily for manual verification.
     */
    @Test
    void testShowSummaryRuns() {
        // Enroll student to at least one course before showing summary
        courseService.enrollStudent("INTRO-CS-1", student);

        // Call showSummary to verify it does not throw exceptions
        courseService.showSummary();
    }

    /**
     * Tests that showEnrolledStudents runs without exceptions
     * when no students are enrolled in the specified course.
     */
    @Test
    void testShowEnrolledStudentsForEmptyCourse() {
        // No students enrolled in INTRO-CS-3, should handle gracefully
        courseService.showEnrolledStudents("INTRO-CS-3");
    }
}

