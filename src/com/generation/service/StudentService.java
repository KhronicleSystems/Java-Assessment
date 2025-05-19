package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Student;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class to manage student registration, enrollment, and grading.
 */
public class StudentService {

    // Stores all registered students, mapped by student ID
    private final Map<String, Student> students = new HashMap<>();

    /**
     * Registers a student in the system.
     *
     * @param student the student to subscribe
     */
    public void subscribeStudent(Student student) {
        students.put(student.getId(), student); // Add student to the map using their ID
    }

    /**
     * Retrieves a student by ID.
     *
     * @param studentId the ID of the student
     * @return the Student object, or null if not found
     */
    public Student findStudent(String studentId) {
        return students.get(studentId); // Return the student if exists
    }

    /**
     * Checks whether a student is subscribed (registered).
     *
     * @param studentId the ID of the student
     * @return true if the student exists in the map, false otherwise
     */
    public boolean isSubscribed(String studentId) {
        return students.containsKey(studentId); // Check presence in the map
    }

    /**
     * Displays a summary of all registered students, including their
     * passed courses and GPA.
     */
    public void showSummary() {
        System.out.println("Students Summary:");

        // Loop through all registered students
        for (Student student : students.values()) {
            System.out.println(student); // Print student details

            // List all passed courses
            System.out.println("Passed Courses:");
            for (Course c : student.findPassedCourses()) {
                System.out.println(" - " + c.getCode() + ": " + c.getName() +
                        ", Credits: " + c.getCredits() +
                        ", Module: " + c.getModule().getName());
            }

            // Print student's GPA
            System.out.printf("GPA: %.2f%n", student.getAverage());
            System.out.println("-------------------------");
        }
    }

    /**
     * Enrolls a student to a course.
     *
     * @param studentId the student's ID
     * @param course    the course to enroll the student in
     * @return true if enrollment was successful, false otherwise
     */
    public boolean enrollToCourse(String studentId, Course course) {
        Student student = students.get(studentId); // Retrieve the student
        if (student == null) return false;         // If student not found, return false
        return student.enrollToCourse(course);     // Delegate enrollment to student object
    }

    /**
     * Assigns a grade to a student for a given course.
     *
     * @param studentId  the student's ID
     * @param courseCode the course code
     * @param grade      the grade to assign
     */
    public void gradeStudentCourse(String studentId, String courseCode, double grade) {
        Student student = students.get(studentId); // Retrieve the student
        if (student != null) {
            student.gradeCourse(courseCode, grade); // Assign grade via student object
        } else {
            // Handle case where student ID is invalid
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }
}
