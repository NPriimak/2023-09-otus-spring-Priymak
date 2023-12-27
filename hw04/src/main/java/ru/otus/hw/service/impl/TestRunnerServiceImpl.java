package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import ru.otus.hw.service.PersistentResultService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestRunnerService;
import ru.otus.hw.service.TestService;

@Service
@ShellComponent
@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final PersistentResultService resultService;

    @Override
    @ShellMethod(
            key = {"test", "startTest", "restartTest"},
            value = "start testing"
    )
    public void run() {
        var student = studentService.determineCurrentStudent();
        var testResult = testService.executeTestFor(student);
        resultService.save(testResult);
        resultService.showResult(testResult);
    }
}
