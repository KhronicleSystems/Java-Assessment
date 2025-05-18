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

    @BeforeEach
    void setUp() {
        courseService = new CourseService();
        studentService = new StudentService();

        Student student = new Student("S001", "John Doe", "john@example.com", null);
        studentService.subscribeStudent(student);

        course = courseService.getCourse("INTRO-CS-1");
        assertNotNull(course, "Course INTRO-CS-1 should be registered");
    }

    @Test
    void testSubscribeAndFindStudent() {
        assertTrue(studentService.isSubscribed("S001"));
        Student found = studentService.findStudent("S001");
        assertNotNull(found);
        assertEquals("John Doe", found.getName());
    }

    @Test
    void testEnrollToCourse() {
        boolean enrolled = studentService.enrollToCourse("S001", course);
        assertTrue(enrolled);

        // Trying to enroll a non-existent student returns false
        assertFalse(studentService.enrollToCourse("S999", course));

        // Trying to enroll again returns false because already enrolled
        assertFalse(studentService.enrollToCourse("S001", course));
    }

    @Test
    void testGradeStudentCourse() {
        // Enroll student first
        studentService.enrollToCourse("S001", course);

        studentService.gradeStudentCourse("S001", course.getCode(), 8.5);

        Student found = studentService.findStudent("S001");
        // Instead of checking grade directly, check that average updated and course is approved
        assertTrue(found.getAverage() >= 8.5);
        assertTrue(found.isCourseApproved(course.getCode()));

        // Grade below passing should NOT approve course
        studentService.gradeStudentCourse("S001", course.getCode(), 5.0);
        // Since approval happens only on passing, approval stays true from before
        // But let's test with a different course for clarity
        Course anotherCourse = courseService.getCourse("INTRO-CS-2");
        studentService.enrollToCourse("S001", anotherCourse);
        studentService.gradeStudentCourse("S001", anotherCourse.getCode(), 5.0);
        assertFalse(found.isCourseApproved(anotherCourse.getCode()));

        // Passing grade should approve that course
        studentService.gradeStudentCourse("S001", anotherCourse.getCode(), 6.5);
        assertTrue(found.isCourseApproved(anotherCourse.getCode()));

        // Grading a non-existent student does not throw exception
        studentService.gradeStudentCourse("S999", course.getCode(), 7.0);
    }

    @Test
    void testShowSummaryRuns() {
        studentService.showSummary();
    }
}

