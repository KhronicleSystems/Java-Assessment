package com.generation.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends Person implements Evaluation {
    private double average;

    private final List<Course> courses = new ArrayList<>();

    private final Map<String, Course> approvedCourses = new HashMap<>();

    private final Map<String, Double> grades = new HashMap<>();

    public Student(String id, String name, String email, Date birthDate) {
        super(id, name, email, birthDate);
    }

    public boolean enrollToCourse(Course course) {
        if (isAttendingCourse(course.getCode())) {
            return false;  // already enrolled
        }
        courses.add(course);
        return true;
    }


    public void registerApprovedCourse(Course course) {
        approvedCourses.put(course.getCode(), course);
    }

    public boolean isCourseApproved(String courseCode) {
        return approvedCourses.containsKey(courseCode);
    }

    public List<Course> findPassedCourses() {
        return new ArrayList<>(approvedCourses.values());
    }

    public boolean isAttendingCourse(String courseCode) {
        for (Course c : courses) {
            if (c.getCode().equals(courseCode)) {
                return true;
            }
        }
        return false;
    }

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

    @Override
    public double getAverage() {
        return average;
    }

    @Override
    public List<Course> getApprovedCourses() {
        return new ArrayList<>(approvedCourses.values());
    }

    @Override
    public String toString() {
        return "Student {" + super.toString() + ", average=" + String.format("%.2f", average) + ", approvedCourses=" + approvedCourses.keySet() + "}";
    }
}
