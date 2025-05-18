package com.generation;

import com.generation.model.Course;
import com.generation.model.Student;
import com.generation.service.CourseService;
import com.generation.service.StudentService;
import com.generation.utils.PrinterHelper;
import java.util.Scanner;

public class Main {

    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final Scanner scanner = new Scanner(System.in);

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        int option;

        do {
            PrinterHelper.showMainMenu();
            option = scanner.nextInt();

            switch (option) {
                case 1 -> registerStudent(studentService, scanner);
                case 2 -> findStudent();
                case 3 -> gradeStudent();
                case 4 -> enrollStudentToCourse();
                case 5 -> showStudentsSummary();
                case 6 -> showCoursesSummary();
                case 7 -> System.out.println("Exiting... Bye!");
                default -> System.out.println("Invalid option, try again.");
            }
        } while (option != 7);

        scanner.close();
    }

    private static void registerStudent(StudentService studentService, Scanner scanner) {
        Student student = PrinterHelper.createStudentMenu(scanner);
        if (student == null) {
            // In case createStudentMenu is changed later to allow cancel, safe check
            return;
        }
        if (studentService.isSubscribed(student.getId())) {
            System.out.println("Student with ID " + student.getId() + " is already registered.");
            return;
        }
        studentService.subscribeStudent(student);
    }

    private static void findStudent() {
        System.out.println("Enter student ID: ");
        String studentId = scanner.next();
        Student student = studentService.findStudent(studentId);
        if (student != null) {
            System.out.println("Student Found: ");
            System.out.println(student);
        } else {
            System.out.println("Student with Id = " + studentId + " not found");
        }
    }

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

        // Show enrolled students before grading
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

        studentService.gradeStudentCourse(studentId, courseCode, grade);
        System.out.println("Grade recorded successfully.");
    }

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
        // Use PrinterHelper to print course info instead of println(course)
        PrinterHelper.printCourseInfo(course);

        boolean studentEnrolled = studentService.enrollToCourse(studentId, course);
        boolean courseEnrolled = courseService.enrollStudent(courseId, student);

        if (!studentEnrolled || !courseEnrolled) {
            System.out.println("Student " + studentId + " is already enrolled in course " + courseId);
        } else {
            System.out.println("Student with ID: " + studentId + " enrolled successfully to " + courseId);
        }
    }

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

    private static void showCoursesSummary() {
        courseService.showSummary();
    }
}



