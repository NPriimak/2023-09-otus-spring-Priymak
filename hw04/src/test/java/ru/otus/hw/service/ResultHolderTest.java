package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Сервис хранения результатов должен ")
class ResultHolderTest {

    @Autowired
    private ResultHolder resultHolder;

    @Test
    @DisplayName("сохранять результаты")
    void save() {
        final var result1 = getResult();
        result1.setRightAnswersCount(10);

        final var result2 = getResult();
        result2.setRightAnswersCount(20);
        assertDoesNotThrow(() -> resultHolder.save(result1));
        assertDoesNotThrow(() -> resultHolder.save(result2));
        assertEquals(2,
                resultHolder.getPreviousResultsForStudent(
                        result1.getStudent().firstName(),
                        result1.getStudent().lastName()
                ).size()
        );
    }

    @Test
    @DisplayName("возвращать все результаты")
    void getAllPreviousResults() {
        final var result1 = new TestResult(new Student("1", "2"));
        result1.setRightAnswersCount(10);

        final var result2 = new TestResult(new Student("3", "4"));
        result2.setRightAnswersCount(20);
        assertDoesNotThrow(() -> resultHolder.save(result1));
        assertDoesNotThrow(() -> resultHolder.save(result2));
        assertEquals(2,
                resultHolder.getAllPreviousResults().size()
        );
    }

    @Test
    @DisplayName("возвращать результаты для конкретного студента")
    void getPreviousResultsForStudent() {
        final var result1 = getResult();
        result1.setRightAnswersCount(10);

        final var result2 = getResult();
        result2.setRightAnswersCount(20);
        assertDoesNotThrow(() -> resultHolder.save(result1));
        assertDoesNotThrow(() -> resultHolder.save(result2));
        assertEquals(2,
                resultHolder.getPreviousResultsForStudent(
                        result1.getStudent().firstName(),
                        result1.getStudent().lastName()
                ).size()
        );
    }

    private TestResult getResult() {
        return new TestResult(new Student("test", "test"));
    }
}