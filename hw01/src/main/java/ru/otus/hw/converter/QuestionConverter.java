package ru.otus.hw.converter;

import org.springframework.util.CollectionUtils;
import ru.otus.hw.domain.Question;

import static java.lang.String.format;

public class QuestionConverter {

    public static final String QUESTION_TEMPLATE = "%d) %s\n";

    public static final String ANSWER_TEMPLATE = "\t• %s\n";

    /**
     * Метод для конвертации вопроса в строку<p>
     * Пример:
     * <p>
     * 1) Is there life on Mars?
     * • Science doesn't know this yet
     * • Certainly. The red UFO is from Mars. And green is from Venus
     * • Absolutely not
     *
     * @param question        вопрос
     * @param questionCounter порядковый номер вопроса
     * @return вопрос в виде строки
     */
    public String convertToString(Question question, int questionCounter) {
        final var sb = new StringBuilder();
        sb.append(format(QUESTION_TEMPLATE, questionCounter, question.text()));
        if (!CollectionUtils.isEmpty(question.answers())) {
            question.answers().forEach(answer -> sb.append(format(ANSWER_TEMPLATE, answer.text())));
        }
        return sb.toString();
    }

}
