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

    @InjectMocks
    private TestServiceImpl testService;

    @DisplayName("бросать ошибку, если список вопрос пустой")
    @Test
    void executeTestWithEmptyQuestionsTest() {
        when(questionDao.findAll()).thenReturn(Collections.emptyList());

        assertThrows(TestServiceException.class, () -> testService.executeTest());
    }

    @DisplayName("вызывать сервис для отобрадения вопросов и не бросать ошибок")
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
        verify(ioService, times(1)).printFormattedLine(anyString());
    }

}