package com.generation;

import com.generation.model.Course;
import com.generation.model.Student;
import com.generation.service.CourseService;
import com.generation.service.StudentService;
import com.generation.utils.PrinterHelper;
import java.util.Scanner;

/**
 * Main class providing a console menu-driven interface
 * for managing students and courses.
 */
public class Main {

    // Static services used throughout the application
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();

    // Scanner for reading user input from the console
    private static final Scanner scanner = new Scanner(System.in);

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        int option;

        // Main loop showing menu options and processing user choices
        do {
            PrinterHelper.showMainMenu();     // Display menu options
            option = scanner.nextInt();       // Read user's menu choice

            switch (option) {
                case 1 -> registerStudent();           // Register a new student
                case 2 -> findStudent();               // Find and show a student
                case 3 -> gradeStudent();              // Grade a student's course
                case 4 -> enrollStudentToCourse();     // Enroll a student in a course
                case 5 -> showStudentsSummary();       // Show summary of students
                case 6 -> showCoursesSummary();        // Show summary of courses
                case 7 -> System.out.println("Exiting... Bye!"); // Exit message
                default -> System.out.println("Invalid option, try again."); // Invalid input handling
            }
        } while (option != 7);  // Continue until user selects exit option

        scanner.close(); // Close Scanner resource before exit
    }

    /**
     * Handles registering a new student.
     * Prompts for student info, checks for duplicates,
     * then registers the student if not already subscribed.
     */
    private static void registerStudent() {
        Student student = PrinterHelper.createStudentMenu(Main.scanner); // Read student info

        if (student == null) {
            // Safe check in case createStudentMenu allows cancel in future
            return;
        }

        if (Main.studentService.isSubscribed(student.getId())) {
            System.out.println("Student with ID " + student.getId() + " is already registered.");
            return;
        }

        // Subscribe/register the new student
        Main.studentService.subscribeStudent(student);
    }

    /**
     * Searches for a student by ID and prints their details if found.
     */
    private static void findStudent() {
        System.out.println("Enter student ID: ");
        String studentId = scanner.next();                     // Read student ID input
        Student student = studentService.findStudent(studentId); // Lookup student

        if (student != null) {
            System.out.println("Student Found: ");
            System.out.println(student);
        } else {
            System.out.println("Student with Id = " + studentId + " not found");
        }
    }

    /**
     * Allows grading a student's course.
     * Validates student, course, enrollment status,
     * and grade input before recording the grade.
     */
    private static void gradeStudent() {
        System.out.println("Enter student ID:");
        String studentId = scanner.next();
        Student student = studentService.findStudent(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Enter course code to grade:");
        String courseCode = scanner.next();
        Course course = courseService.getCourse(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        // Show enrolled students in this course before grading
        courseService.showEnrolledStudents(courseCode);

        if (!student.isAttendingCourse(courseCode)) {
            System.out.println("Student is not enrolled in this course.");
            return;
        }

        System.out.println("Enter grade (0.0 - 10.0):");
        double grade;
        try {
            grade = Double.parseDouble(scanner.next());
        } catch (NumberFormatException e) {
            System.out.println("Invalid grade entered.");
            return;
        }

        if (grade < 0.0 || grade > 10.0) {
            System.out.println("Grade must be between 0.0 and 10.0.");
            return;
        }

        // Record the grade in student's record
        studentService.gradeStudentCourse(studentId, courseCode, grade);
        System.out.println("Grade recorded successfully.");
    }

    /**
     * Enrolls a student to a course by IDs.
     * Checks validity of student and course,
     * then attempts to enroll both in each other's data structures.
     */
    private static void enrollStudentToCourse() {
        System.out.println("Insert student ID");
        String studentId = scanner.next();
        Student student = studentService.findStudent(studentId);

        if (student == null) {
            System.out.println("Invalid Student ID");
            return;
        }
        System.out.println(student);

        System.out.println("Insert course ID");
        String courseId = scanner.next();
        Course course = courseService.getCourse(courseId);

        if (course == null) {
            System.out.println("Invalid Course ID");
            return;
        }

        // Nicely print course info using helper
        PrinterHelper.printCourseInfo(course);

        // Enroll student in course and course in student's record
        boolean studentEnrolled = studentService.enrollToCourse(studentId, course);
        boolean courseEnrolled = courseService.enrollStudent(courseId, student);

        // Check if already enrolled or successfully enrolled
        if (!studentEnrolled || !courseEnrolled) {
            System.out.println("Student " + studentId + " is already enrolled in course " + courseId);
        } else {
            System.out.println("Student with ID: " + studentId + " enrolled successfully to " + courseId);
        }
    }

    /**
     * Shows a summary of all students.
     * Afterward, optionally allows viewing enrolled students in a course.
     */
    private static void showStudentsSummary() {
        studentService.showSummary();

        System.out.println("\nWould you like to view enrolled students for a course? (yes/no):");
        String response = scanner.next().trim().toLowerCase();

        if (response.equals("yes")) {
            System.out.println("Enter course code to view enrolled students:");
            String courseId = scanner.next();
            courseService.showEnrolledStudents(courseId);
        }
    }

    /**
     * Shows a summary of all courses and their enrolled students.
     */
    private static void showCoursesSummary() {
        courseService.showSummary();
    }
}




