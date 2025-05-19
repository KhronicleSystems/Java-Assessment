package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestStudentService {

    private StudentService studentService;
    private Course course;

    /**
     * Sets up a new StudentService and CourseService,
     * subscribes a sample student, and loads a test course.
     */
    @BeforeEach
    void setUp() {
        CourseService courseService = new CourseService();
        studentService = new StudentService();

        Student student = new Student("S001", "John Doe", "john@example.com", null);
        studentService.subscribeStudent(student);

        course = courseService.getCourse("INTRO-CS-1");
        assertNotNull(course, "Course INTRO-CS-1 should be registered");
    }

    /**
     * Tests grading a student who is subscribed but not enrolled in the course.
     * Verifies that grading does not approve the course and GPA remains unchanged.
     */
    @Test
    void testGradeWithoutEnrollment() {
        // Attempt to grade student for a course they are NOT enrolled in
        studentService.gradeStudentCourse("S001", course.getCode(), 7.0);

        Student found = studentService.findStudent("S001");

        // Course should NOT be approved since the student was never enrolled
        assertFalse(found.isCourseApproved(course.getCode()), "Course should not be approved since student was not enrolled.");

        // Average GPA should remain 0.0 because grading should be ignored
        assertEquals(0.0, found.getAverage(), 0.001, "Average should remain 0 since course was not properly graded.");
    }
}

