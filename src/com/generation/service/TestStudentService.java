package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestStudentService {

    private StudentService studentService;
    private Course course;

    @BeforeEach
    void setUp() {
        CourseService courseService = new CourseService();
        studentService = new StudentService();

        Student student = new Student("S001", "John Doe", "john@example.com", null);
        studentService.subscribeStudent(student);

        course = courseService.getCourse("INTRO-CS-1");
        assertNotNull(course, "Course INTRO-CS-1 should be registered");
    }

    @Test
    void testGradeWithoutEnrollment() {
        // Student is subscribed but not enrolled in course
        studentService.gradeStudentCourse("S001", course.getCode(), 7.0);

        Student found = studentService.findStudent("S001");

        // Should not be approved because student wasn't enrolled
        assertFalse(found.isCourseApproved(course.getCode()), "Course should not be approved since student was not enrolled.");

        // GPA should still be 0.0
        assertEquals(0.0, found.getAverage(), 0.001, "Average should remain 0 since course was not properly graded.");
    }
}
