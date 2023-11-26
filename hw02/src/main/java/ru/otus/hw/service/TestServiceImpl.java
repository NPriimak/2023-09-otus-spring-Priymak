package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.hw.converter.QuestionConverter;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.exceptions.QuestionsAreEmptyException;
import ru.otus.hw.exceptions.TestServiceException;

import java.util.List;

import static java.lang.String.format;

@Service
public class TestServiceImpl implements TestService {

    public static final String CLIENT_ERROR_TEMPLATE = "Exception in process of execution program: %s";

    public static final String START_MESSAGE = "\nPlease answer the questions below\n";

    private final IOService ioService;

    private final QuestionDao questionDao;

    private final QuestionConverter questionConverter;

    private final AnswerValidatorService answerValidatorService;


    public TestServiceImpl(IOService ioService,
                           QuestionDao questionDao,
                           QuestionConverter questionConverter,
                           AnswerValidatorService answerValidatorService) {
        this.ioService = ioService;
        this.questionDao = questionDao;
        this.questionConverter = questionConverter;
        this.answerValidatorService = answerValidatorService;
    }

    @Override
    public void executeTest() {
        try {
            final var questions = getQuestionList();

            ioService.printLine(START_MESSAGE);
            var questionCounter = 1;
            for (var question : questions) {
                printQuestionWithAnswers(question, questionCounter);
                questionCounter++;
            }
        } catch (Exception e) {
            throw new TestServiceException(format(CLIENT_ERROR_TEMPLATE, e.getMessage()));
        }
    }

    @Override
    public TestResult executeTestFor(Student student) {
        try {
            final var questions = getQuestionList();
            final var testResult = new TestResult(student);

            ioService.printLine(START_MESSAGE);
            var questionCounter = 1;
            for (var question : questions) {
                final var answer = askQuestion(question, questionCounter);
                final var isCorrect = answerValidatorService.validate(question, answer);
                testResult.applyAnswer(question, isCorrect);
                questionCounter ++;
            }
            return testResult;
        } catch (Exception e) {
            throw new TestServiceException(format(CLIENT_ERROR_TEMPLATE, e.getMessage()));
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
