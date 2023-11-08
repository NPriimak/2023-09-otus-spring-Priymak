package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import ru.otus.hw.converter.QuestionConverter;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionsAreEmptyException;
import ru.otus.hw.exceptions.TestServiceException;

import java.util.List;

import static java.lang.String.format;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    public static final String CLIENT_ERROR_TEMPLATE = "Exception in process of execution program: %s";

    private final IOService ioService;

    private final QuestionDao questionDao;

    private final QuestionConverter questionConverter;

    @Override
    public void executeTest() {
        try {
            final var questions = questionDao.findAll();
            printQuestionsWithAnswers(questions);
        } catch (Exception e) {
            throw new TestServiceException(format(CLIENT_ERROR_TEMPLATE, e.getMessage()));
        }
    }

    /**
     * Метод для печати списка вопросов с вариантами ответов
     *
     * @param questions список впросов с вариантами ответов
     */
    private void printQuestionsWithAnswers(List<Question> questions) {
        if (CollectionUtils.isEmpty(questions)) {
            throw new QuestionsAreEmptyException("There are no questions at all");
        }
        printEmptyLine();
        ioService.printFormattedLine("Please answer the questions below%n");

        var questionCounter = 1;

        for (Question question : questions) {
            final var questionString = questionConverter.convertToString(question, questionCounter);
            ioService.printLine(questionString);
            questionCounter++;
        }
    }

    private void printEmptyLine() {
        ioService.printLine("");
    }
}
