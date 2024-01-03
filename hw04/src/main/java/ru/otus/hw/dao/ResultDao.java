package ru.otus.hw.dao;

import ru.otus.hw.domain.TestResult;

import java.util.List;

/**
 * Сервис для сохранения и получения результатов тетсирования
 */
public interface ResultDao {


    void save(TestResult testResult);

    List<TestResult> getAllPreviousResults();

    List<TestResult> getPreviousResultsForStudent(String firstName, String lastName);

}
