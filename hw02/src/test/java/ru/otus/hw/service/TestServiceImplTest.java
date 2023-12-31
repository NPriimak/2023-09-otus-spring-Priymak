package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.converter.QuestionConverter;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.exceptions.TestServiceException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Сервис тестирования должен ")
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private QuestionConverter converter;

    @Mock
    private AnswerValidatorService answerValidatorService;

    @InjectMocks
    private TestServiceImpl testService;

    @DisplayName("бросать ошибку, если список вопрос пустой")
    @Test
    void executeTestWithEmptyQuestionsTest() {
        when(questionDao.findAll()).thenReturn(Collections.emptyList());

        assertThrows(TestServiceException.class, () -> testService.executeTest());
    }

    @DisplayName("вызывать сервис для отображения вопросов и не бросать ошибок")
    @Test
    void executeTestSuccess() {
        when(converter.convertToString(any(), anyInt())).thenCallRealMethod();
        when(questionDao.findAll())
                .thenReturn(List.of(
                        new Question("1",
                                List.of(
                                        new Answer("1", false),
                                        new Answer("2", false),
                                        new Answer("3", true))),
                        new Question("2",
                                List.of(
                                        new Answer("1", false),
                                        new Answer("2", false),
                                        new Answer("3", true)))
                ));

        assertDoesNotThrow(testService::executeTest);
        verify(ioService, times(3)).printLine(anyString());
    }

    @DisplayName("выводить вопросы пользователям по одному, дожидаться ответа, и определять верный ли он")
    @Test
    void executeTestsForStudentSuccess() {
        final var questions = List.of(
                new Question("1",
                        List.of(
                                new Answer("1", true),
                                new Answer("2", false),
                                new Answer("3", false))),
                new Question("2",
                        List.of(
                                new Answer("1", false),
                                new Answer("2", false),
                                new Answer("3", true)))
        );
        when(converter.convertToString(any(), anyInt())).thenCallRealMethod();
        when(ioService.readStringWithPrompt(anyString())).thenReturn("3");
        when(answerValidatorService.validate(any(), anyString())).thenReturn(true);
        when(questionDao.findAll()).thenReturn(questions);

        final var testStudent = getTestStudent();

        final var res = assertDoesNotThrow(() -> testService.executeTestFor(testStudent));
        assertAll(
                () -> assertEquals(2, res.getAnsweredQuestions().size()),
                () -> assertEquals(2, res.getRightAnswersCount()),
                () -> assertEquals(testStudent.getFullName(), res.getStudent().getFullName()),
                () -> verify(answerValidatorService, times(2)).validate(any(), anyString())
        );
    }

    @DisplayName("выводить вопросы пользователям по одному, дожидаться ответа, даже если ответов в вопросе нет")
    @Test
    void executeTestsForStudentSuccessWithOutAnswers() {
        final var questions = List.of(
                new Question("1",
                        List.of(
                                new Answer("1", true),
                                new Answer("2", false),
                                new Answer("3", false))),
                new Question("2",
                        List.of(
                                new Answer("1", false),
                                new Answer("2", false),
                                new Answer("3", true))),
                new Question("3", Collections.emptyList())
        );
        when(converter.convertToString(any(), anyInt())).thenCallRealMethod();
        when(ioService.readStringWithPrompt(anyString())).thenReturn("3");
        when(answerValidatorService.validate(any(), anyString())).thenReturn(true);
        when(questionDao.findAll()).thenReturn(questions);

        final var testStudent = getTestStudent();

        final var res = assertDoesNotThrow(() -> testService.executeTestFor(testStudent));
        assertAll(
                () -> assertEquals(3, res.getAnsweredQuestions().size()),
                () -> assertEquals(3, res.getRightAnswersCount())
        );
    }

    private Student getTestStudent() {
        return new Student("Naruto", "Uzumaki");
    }

}