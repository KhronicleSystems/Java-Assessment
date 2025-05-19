package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Module;
import com.generation.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages course registration and student enrollments.
 */
public class CourseService {

    // Stores all registered courses, mapped by course code
    private final Map<String, Course> courses = new HashMap<>();

    // Stores students enrolled in each course, mapped by course code
    private final Map<String, List<Student>> enrolledStudents = new HashMap<>();

    /**
     * Constructor initializes and registers all courses and modules.
     */
    public CourseService() {
        // Define the module for introductory computer science courses
        Module module = new Module("INTRO-CS", "Introduction to Computer Science",
                "Introductory module for the generation technical programs");

        // Register computer science courses under the INTRO-CS module
        registerCourse(new Course("INTRO-CS-1", "Introduction to Computer Science", 9, module));
        registerCourse(new Course("INTRO-CS-2", "Introduction to Algorithms", 9, module));
        registerCourse(new Course("INTRO-CS-3", "Algorithm Design and Problem Solving - Introduction", 9, module));
        registerCourse(new Course("INTRO-CS-4", "Algorithm Design and Problem Solving - Advanced", 9, module));
        registerCourse(new Course("INTRO-CS-5", "Terminal Fundamentals", 9, module));
        registerCourse(new Course("INTRO-CS-6", "Source Control Using Git and Github", 9, module));
        registerCourse(new Course("INTRO-CS-7", "Agile Software Development with SCRUM", 9, module));

        // Define the module for introductory web development courses
        Module moduleWebFundamentals = new Module("INTRO-WEB", "Web Development Fundamentals",
                "Introduction to fundamentals of web development");

        // Register web development courses under the INTRO-WEB module
        registerCourse(new Course("INTRO-WEB-1", "Introduction to Web Applications", 9, moduleWebFundamentals));
        registerCourse(new Course("INTRO-WEB-2", "Introduction to HTML", 9, moduleWebFundamentals));
        registerCourse(new Course("INTRO-WEB-3", "Introduction to CSS", 9, moduleWebFundamentals));
        registerCourse(new Course("INTRO-WEB-4", "Advanced HTML", 9, moduleWebFundamentals));
        registerCourse(new Course("INTRO-WEB-5", "Advanced CSS", 9, moduleWebFundamentals));
        registerCourse(new Course("INTRO-WEB-6", "Introduction to Bootstrap Framework", 9, moduleWebFundamentals));
        registerCourse(new Course("INTRO-WEB-7", "Introduction to JavaScript for Web Development", 9, moduleWebFundamentals));
    }

    /**
     * Registers a new course in the system.
     *
     * @param course the course to register
     */
    public void registerCourse(Course course) {
        courses.put(course.getCode(), course); // Store course using its code as key
    }

    /**
     * Retrieves a course by its code.
     *
     * @param code the course code
     * @return the Course object if found, otherwise null
     */
    public Course getCourse(String code) {
        return courses.get(code); // Get course from map by code
    }

    /**
     * Enrolls a student in a course if not already enrolled.
     *
     * @param courseId the course code
     * @param student  the student to enroll
     * @return true if student was successfully enrolled, false if already enrolled
     */
    public boolean enrollStudent(String courseId, Student student) {
        // Get the list of students for this course or create a new one
        List<Student> studentsInCourse = enrolledStudents.computeIfAbsent(courseId, _ -> new ArrayList<>());

        // Avoid enrolling the same student twice
        if (studentsInCourse.contains(student)) {
            return false; // Student already enrolled
        }

        studentsInCourse.add(student); // Add student to course
        return true;
    }

    /**
     * Prints a summary of all registered courses and their enrolled students.
     */
    public void showSummary() {
        System.out.println("Courses Summary:");

        // Loop through all registered courses
        for (Course course : courses.values()) {
            System.out.println(course); // Print course details
            showEnrolledStudents(course.getCode()); // Show students enrolled in this course
        }
    }

    /**
     * Prints the list of students enrolled in a specific course.
     *
     * @param courseId the course code
     */
    public void showEnrolledStudents(String courseId) {
        List<Student> students = enrolledStudents.get(courseId); // Get list of students in course

        // If no students are enrolled, print a message
        if (students == null || students.isEmpty()) {
            System.out.println("  No students enrolled in " + courseId);
            return;
        }

        // Otherwise, list enrolled students
        System.out.println("  Enrolled Students:");
        for (Student student : students) {
            System.out.println("   - " + student); // Print each student
        }
    }
}




