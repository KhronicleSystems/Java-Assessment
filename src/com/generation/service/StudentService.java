package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentService {
    private final Map<String, Student> students = new HashMap<>();

    public void subscribeStudent(Student student) {
        students.put(student.getId(), student);
    }

    public Student findStudent(String studentId) {
        return students.get(studentId);
    }

    public boolean isSubscribed(String studentId) {
        return students.containsKey(studentId);
    }

    public void showSummary() {
        System.out.println("Students Summary:");
        for (Student student : students.values()) {
            System.out.println(student);
            System.out.println("Passed Courses:");
            for (Course c : student.findPassedCourses()) {
                System.out.println(" - " + c.getCode() + ": " + c.getName() + ", Credits: " + c.getCredits() + ", Module: " + c.getModule().getName());
            }
            System.out.printf("GPA: %.2f%n", student.getAverage());
            System.out.println("-------------------------");
        }
    }

    public boolean enrollToCourse(String studentId, Course course) {
        Student student = students.get(studentId);
        if (student == null) return false;
        return student.enrollToCourse(course);
    }

    public void gradeStudentCourse(String studentId, String courseCode, double grade) {
        Student student = students.get(studentId);
        if (student != null) {
            student.gradeCourse(courseCode, grade);
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }
}
