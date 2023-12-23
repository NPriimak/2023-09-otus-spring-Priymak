package ru.otus.hw.exceptions;

public class QuestionsAreEmptyException extends RuntimeException {

    public QuestionsAreEmptyException(String message) {
        super(message);
    }
}
