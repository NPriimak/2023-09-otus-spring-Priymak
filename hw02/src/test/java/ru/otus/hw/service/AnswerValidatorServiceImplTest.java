package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Серивис валдиации клиентских ответов должен ")
class AnswerValidatorServiceImplTest {

    private final AnswerValidatorService answerValidatorService = new AnswerValidatorServiceImpl();

    @Test
    @DisplayName("правильно валидирвать ответы клиентов")
    void validateSuccess() {
        final var question = new Question("1", List.of(
                new Answer("a", true),
                new Answer("b", false)
        ));

        assertAll(
                () -> assertTrue(answerValidatorService.validate(question, "a")),
                () -> assertTrue(answerValidatorService.validate(question, "A")),
                () -> assertFalse(answerValidatorService.validate(question, "b")),
                () -> assertFalse(answerValidatorService.validate(question, "duob")),
                () -> assertFalse(answerValidatorService.validate(question, ""))
        );

    }

    @Test
    @DisplayName("возвращать false если правильного ответа нет")
    void validateWithoutAnswer() {
        final var question = new Question("1", Collections.emptyList());

        assertAll(
                () -> assertFalse(answerValidatorService.validate(question, "very smart answer"))
        );
    }
}