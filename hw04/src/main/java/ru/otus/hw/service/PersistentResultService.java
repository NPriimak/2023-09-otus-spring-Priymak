package ru.otus.hw.service;

import ru.otus.hw.domain.TestResult;

import java.util.List;

public interface PersistentResultService {
    void save(TestResult testResult);

    List<TestResult> getAllPreviousResults();

    List<TestResult> getPreviousResultsForStudent(String firstName, String lastName);
}
