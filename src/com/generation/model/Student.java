package com.generation.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a student, extending from the Person class and implementing Evaluation.
 * A student can enroll in courses, receive grades, and track approved/passed courses.
 */
public class Student extends Person implements Evaluation {

    // Average grade across all graded courses
    private double average;

    // List of currently enrolled courses
    private final List<Course> courses = new ArrayList<>();

    // Map of approved (passed) courses by course code
    private final Map<String, Course> approvedCourses = new HashMap<>();

    // Map of grades by course code
    private final Map<String, Double> grades = new HashMap<>();

    /**
     * Constructs a new Student object.
     *
     * @param id        the student ID
     * @param name      the student's name
     * @param email     the student's email
     * @param birthDate the student's birth date
     */
    public Student(String id, String name, String email, Date birthDate) {
        super(id, name, email, birthDate);
    }

    /**
     * Enrolls the student in a course if not already enrolled.
     *
     * @param course the course to enroll in
     * @return true if enrollment is successful, false if already enrolled
     */
    public boolean enrollToCourse(Course course) {
        if (isAttendingCourse(course.getCode())) {
            return false;  // already enrolled
        }
        courses.add(course);
        return true;
    }

    /**
     * Registers a course as approved for the student.
     *
     * @param course the course to register as approved
     */
    public void registerApprovedCourse(Course course) {
        approvedCourses.put(course.getCode(), course);
    }

    /**
     * Checks if a course has been approved by the student.
     *
     * @param courseCode the course code to check
     * @return true if the course is approved, false otherwise
     */
    public boolean isCourseApproved(String courseCode) {
        return approvedCourses.containsKey(courseCode);
    }

    /**
     * Returns a list of all approved (passed) courses.
     *
     * @return list of approved courses
     */
    public List<Course> findPassedCourses() {
        return new ArrayList<>(approvedCourses.values());
    }

    /**
     * Checks if the student is currently attending a course.
     *
     * @param courseCode the course code to check
     * @return true if enrolled in the course, false otherwise
     */
    public boolean isAttendingCourse(String courseCode) {
        for (Course c : courses) {
            if (c.getCode().equals(courseCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Grades a course for the student. If the grade is >= 6.0, the course is marked as approved.
     * Updates the student's average grade after grading.
     *
     * @param courseCode the course code to grade
     * @param grade      the grade to assign
     */
    public void gradeCourse(String courseCode, double grade) {
        if (!isAttendingCourse(courseCode)) {
            System.out.println("Student is not attending course: " + courseCode);
            return;
        }
        grades.put(courseCode, grade);
        if (grade >= 6.0) {
            Course courseToApprove = null;
            for (Course c : courses) {
                if (c.getCode().equals(courseCode)) {
                    courseToApprove = c;
                    break;
                }
            }
            if (courseToApprove != null && !isCourseApproved(courseCode)) {
                registerApprovedCourse(courseToApprove);
            }
        }
        updateAverage();
    }

    /**
     * Updates the average grade based on all graded courses.
     */
    private void updateAverage() {
        if (grades.isEmpty()) {
            average = 0.0;
            return;
        }
        double sum = 0;
        for (double g : grades.values()) {
            sum += g;
        }
        average = sum / grades.size();
    }

    /**
     * Returns the student's average grade.
     *
     * @return average grade
     */
    @Override
    public double getAverage() {
        return average;
    }

    /**
     * Returns a list of the student's approved courses.
     *
     * @return list of approved courses
     */
    @Override
    public List<Course> getApprovedCourses() {
        return new ArrayList<>(approvedCourses.values());
    }

    /**
     * Returns a string representation of the student.
     *
     * @return formatted string with student info
     */
    @Override
    public String toString() {
        return "Student {" + super.toString() + ", average=" + String.format("%.2f", average) + ", approvedCourses=" + approvedCourses.keySet() + "}";
    }
}

