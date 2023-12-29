package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.converter.TestResultConverter;
import ru.otus.hw.domain.TestResultDto;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class TestControllerImpl implements TestController {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final TestResultConverter testResultConverter;

    @Override
    @ShellMethod(
            key = {"test", "startTest", "restartTest"},
            value = "start testing"
    )
    public void run() {
        final var student = studentService.determineCurrentStudent();
        final var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }

    @Override
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

    @Override
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
