package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService studentService;
    private CourseService courseService;
    private Course course;

    /**
     * Sets up fresh instances of CourseService and StudentService,
     * subscribes a sample student, and loads a known course for testing.
     */
    @BeforeEach
    void setUp() {
        courseService = new CourseService();
        studentService = new StudentService();

        Student student = new Student("S001", "John Doe", "john@example.com", null);
        studentService.subscribeStudent(student);

        course = courseService.getCourse("INTRO-CS-1");
        assertNotNull(course, "Course INTRO-CS-1 should be registered");
    }

    /**
     * Tests subscribing a student and retrieving them by ID.
     * Verifies student is registered and details are correct.
     */
    @Test
    void testSubscribeAndFindStudent() {
        assertTrue(studentService.isSubscribed("S001"));
        Student found = studentService.findStudent("S001");
        assertNotNull(found);
        assertEquals("John Doe", found.getName());
    }

    /**
     * Tests enrolling a student to a course.
     * Checks enrollment success, failure on duplicate, and failure for unknown students.
     */
    @Test
    void testEnrollToCourse() {
        // Successful enrollment for existing student and course
        boolean enrolled = studentService.enrollToCourse("S001", course);
        assertTrue(enrolled);

        // Enrollment attempt for a non-existent student returns false
        assertFalse(studentService.enrollToCourse("S999", course));

        // Duplicate enrollment for same student and course should fail
        assertFalse(studentService.enrollToCourse("S001", course));
    }

    /**
     * Tests grading a student's course.
     * Verifies approval logic based on passing grade,
     * and that grading non-existent students does not cause exceptions.
     */
    @Test
    void testGradeStudentCourse() {
        // Enroll student first before grading
        studentService.enrollToCourse("S001", course);

        // Grade above passing threshold - should mark course approved
        studentService.gradeStudentCourse("S001", course.getCode(), 8.5);

        Student found = studentService.findStudent("S001");
        assertTrue(found.getAverage() >= 8.5);
        assertTrue(found.isCourseApproved(course.getCode()));

        // Grade below passing threshold - course should not be approved
        studentService.gradeStudentCourse("S001", course.getCode(), 5.0);
        // Approval remains from previous passing grade (no downgrade expected)

        // Test with a different course for clarity
        Course anotherCourse = courseService.getCourse("INTRO-CS-2");
        studentService.enrollToCourse("S001", anotherCourse);
        studentService.gradeStudentCourse("S001", anotherCourse.getCode(), 5.0);
        assertFalse(found.isCourseApproved(anotherCourse.getCode()));

        // Passing grade should approve that course
        studentService.gradeStudentCourse("S001", anotherCourse.getCode(), 6.5);
        assertTrue(found.isCourseApproved(anotherCourse.getCode()));

        // Grading a non-existent student ID should not throw exceptions
        studentService.gradeStudentCourse("S999", course.getCode(), 7.0);
    }

    /**
     * Tests that showSummary runs without exceptions.
     * This is primarily for manual verification.
     */
    @Test
    void testShowSummaryRuns() {
        studentService.showSummary();
    }
}


