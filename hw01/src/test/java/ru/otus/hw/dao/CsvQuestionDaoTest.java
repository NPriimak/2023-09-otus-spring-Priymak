package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("ДАО для чтения из .csv должно ")
@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    @Mock
    private TestFileNameProvider testFileNameProvider;

    @InjectMocks
    private CsvQuestionDao questionDao;


    @DisplayName("успешно прочитать все вопросы")
    @Test
    void findAllSuccessfullyTest() {
        when(testFileNameProvider.getTestFileName()).thenReturn("testQuestions.csv");
        final var res = assertDoesNotThrow(questionDao::findAll);


        assertAll(
                () -> assertEquals(3, res.size()),
                () -> assertTrue(res.stream().noneMatch(el -> CollectionUtils.isEmpty(el.answers())))
        );
    }

    @DisplayName("бросать ошибку если получен неправильный файл")
    @Test
    void throwExceptionWithIllegalFileTest() {
        when(testFileNameProvider.getTestFileName()).thenReturn("testQuestionsIllegal.csv");
        assertThrows(QuestionReadException.class, questionDao::findAll);
    }

    @DisplayName("бросать ошибку если файла нет")
    @Test
    void throwExceptionWithOutFileTest() {
        when(testFileNameProvider.getTestFileName()).thenReturn("none.csv");
        assertThrows(QuestionReadException.class, questionDao::findAll);
    }

    @DisplayName("бросать ошибку, если передан файл с неверным расширением")
    @Test
    void throwExceptionIfFileHasWrongExtTest(){
        when(testFileNameProvider.getTestFileName()).thenReturn("testQuestions.txt");
        assertThrows(QuestionReadException.class, questionDao::findAll);
    }
}