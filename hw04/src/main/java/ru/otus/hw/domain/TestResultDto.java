package ru.otus.hw.domain;

public record TestResultDto(String studentFullName,
                            int questionCount,
                            int rightQuestionCount) {
}
