package ru.otus.hw.converter;

import org.springframework.stereotype.Component;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.domain.TestResultDto;

@Component
public class TestResultConverter {

    /**
     * Convert test result to dto
     *
     * @param testResult test result
     * @return dto of test result
     */
    public TestResultDto toDto(TestResult testResult) {
        return new TestResultDto(
                testResult.getStudent().getFullName(),
                testResult.getAnsweredQuestions().size(),
                testResult.getRightAnswersCount()
        );
    }
}
