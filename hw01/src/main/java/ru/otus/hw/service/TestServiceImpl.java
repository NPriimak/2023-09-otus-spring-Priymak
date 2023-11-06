package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionsAreEmptyException;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    public static final String QUESTION_TEMPLATE = "%d) %s";

    public static final String ANSWER_TEMPLATE = "\t• %s";

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        final var questions = questionDao.findAll();
        printQuestionsWithAnswers(questions);
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

        var questionCounter = 1;

        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        for (Question question : questions) {
            ioService.printFormattedLine(QUESTION_TEMPLATE, questionCounter, question.text());
            printAnswers(question);
            ioService.printLine("");
            questionCounter++;
        }
    }

    private void printAnswers(Question question) {
        if (CollectionUtils.isEmpty(question.answers())) {
            return;
        }
        question.answers().forEach(answer -> ioService.printFormattedLine(ANSWER_TEMPLATE, answer.text()));
    }
}
