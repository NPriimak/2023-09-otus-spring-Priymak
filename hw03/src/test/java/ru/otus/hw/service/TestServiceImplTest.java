package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.base.AbstractSpringBootTest;
import ru.otus.hw.converter.QuestionConverter;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.exceptions.TestServiceException;
import ru.otus.hw.service.impl.TestServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Сервис тестирования должен ")
class TestServiceImplTest extends AbstractSpringBootTest {

    @MockBean
    private IOService ioService;

    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private QuestionConverter converter;

    @MockBean
    private AnswerValidatorService answerValidatorService;

    @Autowired
    private TestServiceImpl testService;

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