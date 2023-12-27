package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.domain.TestResultDto;
import ru.otus.hw.service.PersistentResultService;
import ru.otus.hw.service.ResultHolder;
import ru.otus.hw.service.ResultService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
@Service
public class PersistentResultServiceImpl implements PersistentResultService {

    private final ResultService resultService;

    private final ResultHolder resultHolder;

    @Override
    public void save(TestResult testResult) {
        resultHolder.save(testResult);
    }

    @Override
    @ShellMethod(
            key = {"results", "testResults"},
            value = "Get all previous test results"
    )
    public List<TestResultDto> getAllPreviousResults() {
        return resultHolder.getAllPreviousResults();
    }

    @Override
    @ShellMethod(
            key = {"resultsForStudent", "testResultsForStudent"},
            value = "Get all previous results for specific student"
    )
    public List<TestResultDto> getPreviousResultsForStudent(@ShellOption(value = "firstName") String firstName,
                                                            @ShellOption(value = "lastName") String lastName) {
        return resultHolder.getPreviousResultsForStudent(firstName, lastName);
    }

    @Override
    public void showResult(TestResult testResult) {
        resultService.showResult(testResult);
    }
}
