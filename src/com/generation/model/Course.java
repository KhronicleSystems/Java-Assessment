package com.generation.model;

/**
 * The Course class represents a course within a specific module,
 * containing information like course code, name, credit value, and module association.
 */
public class Course {

    // Unique identifier for the course
    private final String code;

    // Name or title of the course
    private final String name;

    // Number of credits the course is worth
    private final int credits;

    // The module this course belongs to
    private final Module module;

    /**
     * Constructor to create a new Course instance with specified details.
     *
     * @param code    Unique course code
     * @param name    Name of the course
     * @param credits Number of credits awarded for completing the course
     * @param module  Module to which the course belongs
     */
    public Course(String code, String name, int credits, Module module) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.module = module;
    }

    /**
     * @return the unique course code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the name/title of the course
     */
    public String getName() {
        return name;
    }

    /**
     * @return number of credits assigned to the course
     */
    public int getCredits() {
        return credits;
    }

    /**
     * @return the module object the course is associated with
     */
    public Module getModule() {
        return module;
    }

    /**
     * Returns a string representation of the course, including its code, name, credits, and module name.
     *
     * @return a string summary of the course
     */
    @Override
    public String toString() {
        return "Course{" +
                "code='" + getCode() + '\'' +
                ", name='" + getName() + '\'' +
                ", credits=" + getCredits() +
                ", module=" + (getModule() != null ? getModule().getName() : "None") +
                '}';
    }
}
