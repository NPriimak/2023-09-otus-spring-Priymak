package ru.otus.hw.service;

import ru.otus.hw.domain.TestResult;

import java.util.List;

public interface TestRunnerService {
    void run();

    List<TestResult> getAllPreviousResults();

    List<TestResult> getPreviousResultsForStudent(String firstName, String lastName);
}
