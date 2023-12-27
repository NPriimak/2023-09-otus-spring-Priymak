package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.domain.TestResultDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Сервис по работе с резутатами тестов должен ")
class PersistentResultServiceTest {

    @MockBean
    private ResultService service;
    @MockBean
    private ResultHolder resultHolder;

    @Autowired
    private PersistentResultService resultService;

    @Test
    @DisplayName("сохранять результаты")
    void save() {
        final var testResult = new TestResult(new Student("test", "test"));
        assertDoesNotThrow(() -> resultService.save(testResult));
        verify(resultHolder, times(1)).save(eq(testResult));
    }

    @Test
    @DisplayName("вовзращать все рузльтаты тестов")
    void getAllPreviousResults() {
        when(resultHolder.getAllPreviousResults())
                .thenReturn(List.of(
                        new TestResultDto("test", 10, 5),
                        new TestResultDto("test", 10, 6)
                ));

        final var res = assertDoesNotThrow(() -> resultService.getAllPreviousResults());
        verify(resultHolder, times(1)).getAllPreviousResults();
        assertEquals(2, res.size());
    }

    @Test
    @DisplayName("возвращать результаты для конкретного студента")
    void getPreviousResultsForStudent() {
        when(resultHolder.getPreviousResultsForStudent(eq("test"), eq("test")))
                .thenReturn(List.of(
                        new TestResultDto("test", 10, 5),
                        new TestResultDto("test", 10, 6)
                ));

        when(resultHolder.getPreviousResultsForStudent(eq("atest"), eq("atest")))
                .thenReturn(Collections.emptyList());

        final var res1 = assertDoesNotThrow(() -> resultService.getPreviousResultsForStudent("test", "test"));
        assertEquals(2, res1.size());

        final var res2 = assertDoesNotThrow(() -> resultService.getPreviousResultsForStudent("atest", "atest"));
        assertEquals(0, res2.size());
    }

    @Test
    @DisplayName("показывать результаты")
    void showResult() {
        final var testResult = new TestResult(new Student("test", "test"));

        assertDoesNotThrow(() -> resultService.showResult(testResult));
        verify(service, times(1)).showResult(eq(testResult));
    }
}