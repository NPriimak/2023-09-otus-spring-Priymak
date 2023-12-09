package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.hw.converter.QuestionConverter;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.exceptions.QuestionsAreEmptyException;
import ru.otus.hw.exceptions.TestServiceException;
import ru.otus.hw.service.AnswerValidatorService;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.LocalizedMessageSupplier;
import ru.otus.hw.service.TestService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private static final String START_MESSAGE_KEY = "test.startMessage";

    private static final String CLIENT_ERROR_KEY = "test.clientError";

    private final LocalizedIOServiceImpl ioService;

    private final QuestionDao questionDao;

    private final QuestionConverter questionConverter;

    private final AnswerValidatorService answerValidatorService;

    @Override
    public TestResult executeTestFor(Student student) {
        try {
            final var questions = getQuestionList();
            final var testResult = new TestResult(student);

            ioService.printLineLocalized(START_MESSAGE_KEY);
            var questionCounter = 1;
            for (var question : questions) {
                final var answer = askQuestion(question, questionCounter);
                final var isCorrect = answerValidatorService.validate(question, answer);
                testResult.applyAnswer(question, isCorrect);
                questionCounter ++;
            }
            return testResult;
        } catch (Exception e) {
            log.error("Exception in process of executing test: {}", e.getMessage(), e);
            ioService.printLineLocalized(CLIENT_ERROR_KEY);
            throw new TestServiceException(e.getMessage());
        }
    }


    /**
     * Метод для вывода запроса и ожадиния ответа пользователя
     * @param question вопрос
     * @param questionCounter номер вопроса
     * @return ответ пользователя
     */
    private String askQuestion(Question question, int questionCounter) {
        final var questionString = questionConverter.convertToString(question, questionCounter);
        return ioService.readStringWithPrompt(questionString).trim();
    }

    /**
     * Получение списка вопросов
     *
     * @return список вопросов или кидает ошибку, если вопросов не обнаружено
     */
    private List<Question> getQuestionList() {
        final var questions = questionDao.findAll();

        if (CollectionUtils.isEmpty(questions)) {
            throw new QuestionsAreEmptyException("There are no questions at all");
        }

        return questions;
    }

    /**
     * Метод для печати списка вопроса с вариантами ответов
     *
     * @param question        впрос с вариантами ответов
     * @param questionCounter номер вопроса
     */
    private void printQuestionWithAnswers(Question question, int questionCounter) {
        final var questionString = questionConverter.convertToString(question, questionCounter);
        ioService.printLine(questionString);
    }
}
