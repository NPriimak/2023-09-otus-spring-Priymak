package ru.otus.hw.service;

public interface StudentRegistrationFacade {

    /**
     * Retrieve first name from student
     * @return first name
     */
    String askFirstName();

    /**
     * Retrieve second name from student
     * @return second name
     */
    String askLastName();

}
