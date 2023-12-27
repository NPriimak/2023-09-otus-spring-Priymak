package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.otus.hw.converter.TestResultConverter;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.domain.TestResultDto;
import ru.otus.hw.service.ResultHolder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Primary
public class InMemoryResultHolderImpl implements ResultHolder {

    //Т.к. конкурентного доступа не подразумевается, используем обычную мапу
    private final Map<Student, List<TestResult>> resultMap = new HashMap<>();

    private final TestResultConverter resultConverter;

    @Override
    public void save(TestResult testResult) {
        resultMap.merge(
                testResult.getStudent(),
                List.of(testResult),
                (prev, next) -> Stream.concat(prev.stream(), next.stream()).toList()
        );
    }

    @Override
    public List<TestResultDto> getAllPreviousResults() {
        return resultMap.values()
                .stream()
                .flatMap(List::stream)
                .map(resultConverter::toDto)
                .toList();
    }

    @Override
    public List<TestResultDto> getPreviousResultsForStudent(String firstName, String lastName) {
        return ofNullable(resultMap.get(new Student(firstName, lastName)))
                .map(results -> results.stream().map(resultConverter::toDto).toList())
                .orElseGet(Collections::emptyList);
    }
}
