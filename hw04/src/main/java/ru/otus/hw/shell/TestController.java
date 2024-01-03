package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.converter.TestResultConverter;
import ru.otus.hw.domain.TestResultDto;
import ru.otus.hw.service.TestRunnerService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class TestController {

    private final TestRunnerService testService;

    private final TestResultConverter testResultConverter;

    @ShellMethod(
            key = {"test", "startTest", "restartTest"},
            value = "start testing"
    )
    public void run() {
        testService.run();
    }

    @ShellMethod(
            key = {"results", "testResults"},
            value = "Get all previous test results"
    )
    public List<TestResultDto> getAllPreviousResults() {
        return testService.getAllPreviousResults()
                .stream()
                .map(testResultConverter::toDto)
                .toList();
    }

    @ShellMethod(
            key = {"resultsForStudent", "testResultsForStudent"},
            value = "Get all previous results for specific student"
    )
    public List<TestResultDto> getPreviousResultsForStudent(@ShellOption(value = "firstName") String firstName,
                                                            @ShellOption(value = "lastName") String lastName) {
        return testService.getPreviousResultsForStudent(firstName, lastName)
                .stream()
                .map(testResultConverter::toDto)
                .toList();
    }
}
