package ru.otus.hw.service;

import ru.otus.hw.domain.Question;

/**
 * Сервис для валидации ответов пользователя
 */
public interface AnswerValidatorService {

    /**
     * Метод для валидации ответов пользователя
     * @param question вопрос
     * @param clientAnswer ответ пользователя
     * @return true - если ответ верен
     */
    boolean validate(Question question, String clientAnswer);
}
