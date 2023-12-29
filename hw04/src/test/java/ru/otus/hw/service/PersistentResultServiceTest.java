package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.ResultDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Сервис по работе с резутатами тестов должен ")
class PersistentResultServiceTest {

    @MockBean
    private ResultDao resultDao;

    @Autowired
    private PersistentResultService resultService;

    @Test
    @DisplayName("сохранять результаты")
    void save() {
        final var testResult = new TestResult(new Student("test", "test"));
        assertDoesNotThrow(() -> resultService.save(testResult));
        verify(resultDao, times(1)).save(eq(testResult));
    }

    @Test
    @DisplayName("вовзращать все рузльтаты тестов")
    void getAllPreviousResults() {
        when(resultDao.getAllPreviousResults())
                .thenReturn(List.of(
                        new TestResult(new Student("test", "test")),
                        new TestResult(new Student("atest", "atest"))
                ));

        final var res = assertDoesNotThrow(() -> resultService.getAllPreviousResults());
        verify(resultDao, times(1)).getAllPreviousResults();
        assertEquals(2, res.size());
    }

    @Test
    @DisplayName("возвращать результаты для конкретного студента")
    void getPreviousResultsForStudent() {
        when(resultDao.getPreviousResultsForStudent(eq("test"), eq("test")))
                .thenReturn(List.of(
                        new TestResult(new Student("test", "test")),
                        new TestResult(new Student("atest", "atest"))
                ));

        when(resultDao.getPreviousResultsForStudent(eq("atest"), eq("atest")))
                .thenReturn(Collections.emptyList());

        final var res1 = assertDoesNotThrow(() -> resultService.getPreviousResultsForStudent("test", "test"));
        assertEquals(2, res1.size());

        final var res2 = assertDoesNotThrow(() -> resultService.getPreviousResultsForStudent("atest", "atest"));
        assertEquals(0, res2.size());
    }
}