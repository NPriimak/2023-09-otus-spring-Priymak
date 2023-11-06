package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ДАО для чтения из .csv должно ")
@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    @DisplayName("успешно прочитать все вопросы")
    @Test
    void findAllSuccessfullyTest() {
        final var questionDao = new CsvQuestionDao(() -> "testQuestions.csv");
        final var res = assertDoesNotThrow(questionDao::findAll);


        assertAll(
                () -> assertEquals(3, res.size()),
                () -> assertTrue(res.stream().noneMatch(el -> CollectionUtils.isEmpty(el.answers())))
        );
    }

    @DisplayName("бросать ошибку если получен неправильный файл")
    @Test
    void throwExceptionWithIllegalFileTest() {
        final var questionDao = new CsvQuestionDao(() -> "testQuestionsIllegal.csv");
        assertThrows(QuestionReadException.class, questionDao::findAll);
    }

    @DisplayName("бросать ошибку если файла нет")
    @Test
    void throwExceptionWithOutFileTest() {
        final var questionDao = new CsvQuestionDao(() -> "none.csv");
        assertThrows(QuestionReadException.class, questionDao::findAll);
    }

    @DisplayName("бросать ошибку, если передан файл с неверным расширением")
    @Test
    void throwExceptionIfFileHasWrongExtTest(){
        final var questionDao = new CsvQuestionDao(() -> "testQuestions.txt");
        assertThrows(QuestionReadException.class, questionDao::findAll);
    }
}