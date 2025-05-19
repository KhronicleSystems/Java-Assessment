package com.generation.model;

import java.util.List;

/**
 * The Evaluation interface defines the contract for objects
 * (typically students) that can be evaluated based on their
 * course performance.
 */
public interface Evaluation
{
    /**
     * Returns the average grade of the evaluated object.
     *
     * @return the average grade as a double
     */
    double getAverage();

    /**
     * Returns a list of courses that have been approved (i.e., passed).
     *
     * @return a list of approved Course objects
     */
    List<Course> getApprovedCourses();
}
