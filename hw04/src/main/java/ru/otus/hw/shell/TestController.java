package ru.otus.hw.shell;

import ru.otus.hw.domain.TestResultDto;

import java.util.List;

public interface TestController {
    void run();

    List<TestResultDto> getAllPreviousResults();

    List<TestResultDto> getPreviousResultsForStudent(String firstName,
                                                  String lastName);
}
