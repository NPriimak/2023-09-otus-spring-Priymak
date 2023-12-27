package ru.otus.hw.service;

import ru.otus.hw.domain.TestResult;
import ru.otus.hw.domain.TestResultDto;

import java.util.List;

/**
 * Сервис для сохранения и получения результатов тетсирования
 */
public interface ResultHolder {

    /**
     * save test result
     * @param testResult result of test
     */
    void save(TestResult testResult);

    /**
     * Get all previous testing results
     * @return List of testing results or empty list
     */
    List<TestResultDto> getAllPreviousResults();

    /**
     * Get all results of testing for student
     * @param firstName student first name
     * @param lastName student last name
     * @return List with results or empty list
     */
    List<TestResultDto> getPreviousResultsForStudent(String firstName, String lastName);

}
