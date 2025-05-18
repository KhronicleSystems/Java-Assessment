package com.generation.utils;

import com.generation.model.Course;
import com.generation.model.Student;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PrinterHelper {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public static void showMainMenu() {
        System.out.println("|-------------------------------|");
        System.out.println("| Welcome to StudentGen         |");
        System.out.println("|-------------------------------|");
        System.out.println("| Select 1 option:              |");
        System.out.println("| . 1 Register Student          |");
        System.out.println("| . 2 Find Student              |");
        System.out.println("| . 3 Grade Student             |");
        System.out.println("| . 4 Enroll Student to Course  |");
        System.out.println("| . 5 Show Students Summary     |");
        System.out.println("| . 6 Show Courses Summary      |");
        System.out.println("| . 7 Exit                      |");
        System.out.println("|-------------------------------|");
    }

    public static Student createStudentMenu(Scanner scanner) {
        System.out.println("|-------------------------------------|");
        System.out.println("| . 1 Register Student                |");
        System.out.println("|-------------------------------------|");

        scanner.nextLine(); // Consume leftover newline before reading lines

        System.out.print("| Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("| Enter student ID: ");
        String id = scanner.nextLine();

        // Validate email
        String email;
        while (true) {
            System.out.print("| Enter student email: ");
            email = scanner.nextLine();
            if (EMAIL_PATTERN.matcher(email).matches()) {
                break;
            }
            System.out.println("| Invalid email format, please try again.");
        }

        System.out.print("| Enter student birth date (dd/MM/yyyy): ");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        Date birthDate = null;

        while (birthDate == null) {
            try {
                birthDate = formatter.parse(scanner.nextLine());
            } catch (ParseException e) {
                System.out.print("| Invalid date format. Please use dd/MM/yyyy: ");
            }
        }

        System.out.println("|-------------------------------------|");

        // Confirm student info before creating
        System.out.println("Please confirm student details:");
        System.out.println("Name      : " + name);
        System.out.println("ID        : " + id);
        System.out.println("Email     : " + email);
        System.out.println("Birth Date: " + formatter.format(birthDate));
        System.out.print("Is this information correct? (Y/N): ");
        String confirmation = scanner.nextLine().trim().toUpperCase();
        if (!confirmation.equals("Y")) {
            System.out.println("Student creation cancelled.");
            return null;
        }

        Student student = new Student(id, name, email, birthDate);
        System.out.println("Student Successfully Registered!");
        System.out.println(student);
        return student;
    }

    // New helper to print course info nicely
    public static void printCourseInfo(Course course) {
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        System.out.println("Course Information:");
        System.out.println("  Code   : " + course.getCode());
        System.out.println("  Name   : " + course.getName());
        System.out.println("  Credits: " + course.getCredits());
        if (course.getModule() != null) {
            System.out.println("  Module : " + course.getModule().getName() + " (" + course.getModule().getCode() + ")");
        }
    }
}

