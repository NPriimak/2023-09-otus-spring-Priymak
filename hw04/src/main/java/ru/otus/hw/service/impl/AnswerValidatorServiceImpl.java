package ru.otus.hw.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.AnswerValidatorService;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class AnswerValidatorServiceImpl implements AnswerValidatorService {

    @Override
    public boolean validate(Question question, String clientAnswer) {
        if (isNull(question)) {
            throw new IllegalArgumentException("Question is null");
        }

        final var correctAnswer = getCorrectAnswer(question);

        // Мы помним, что могут быть вопросы без ответов. Пока что такие кейсы отмечаем как неправильные
        return correctAnswer.map(answer -> answer.text().equalsIgnoreCase(clientAnswer))
                .orElse(false);
    }

    /**
     * Метод для получения правильного ответа на вопрос
     * @param question вопрос
     * @return правильный ответ или Optional.empty() если ответов нет
     */
    private Optional<Answer> getCorrectAnswer(Question question) {
        if (CollectionUtils.isEmpty(question.answers())) {
            return Optional.empty();
        }

        return question.answers().stream()
                .filter(Answer::isCorrect)
                .findAny();
    }
}
