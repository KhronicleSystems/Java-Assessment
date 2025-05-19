package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TestCourseServiceConsoleOutput {

    private CourseService courseService;
    private Course course;
    private Student student1;
    private Student student2;

    /**
     * Sets up a fresh CourseService instance, retrieves a course,
     * and creates two sample students before each test.
     */
    @BeforeEach
    void setUp() {
        courseService = new CourseService();
        course = courseService.getCourse("INTRO-CS-2");
        assertNotNull(course, "Course INTRO-CS-2 should be registered");

        student1 = new Student("S100", "Alice Smith", "alice@example.com", new Date());
        student2 = new Student("S101", "Bob Johnson", "bob@example.com", new Date());
    }

    /**
     * Tests enrolling two students and verifies that
     * the console output of showEnrolledStudents contains their names.
     */
    @Test
    void testEnrollStudentsAndVerifyConsoleOutput() {
        // Enroll both students in the course
        assertTrue(courseService.enrollStudent(course.getCode(), student1), "Student 1 should be enrolled");
        assertTrue(courseService.enrollStudent(course.getCode(), student2), "Student 2 should be enrolled");

        // Capture the console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Call the method that prints enrolled students to console
        courseService.showEnrolledStudents(course.getCode());

        // Restore original System.out to avoid side effects on other tests
        System.setOut(originalOut);

        String output = outContent.toString();

        // Verify the output contains the names of both students
        assertTrue(output.contains(student1.getName()), "Output should contain student 1's name");
        assertTrue(output.contains(student2.getName()), "Output should contain student 2's name");
    }
}
