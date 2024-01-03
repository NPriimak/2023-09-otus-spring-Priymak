package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.PersistentResultService;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestRunnerService;
import ru.otus.hw.service.TestService;

import java.util.List;

@Service
@ShellComponent
@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final PersistentResultService persistentResultService;

    private final ResultService resultService;

    public void run() {
        var student = studentService.determineCurrentStudent();
        var testResult = testService.executeTestFor(student);
        persistentResultService.save(testResult);
        resultService.showResult(testResult);
    }

    @Override
    public List<TestResult> getAllPreviousResults() {
        return persistentResultService.getAllPreviousResults();
    }

    @Override
    public List<TestResult> getPreviousResultsForStudent(String firstName, String lastName) {
        return persistentResultService.getPreviousResultsForStudent(firstName, lastName);
    }
}
