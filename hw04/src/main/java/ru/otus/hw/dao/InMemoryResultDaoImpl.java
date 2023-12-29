package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Primary
public class InMemoryResultDaoImpl implements ResultDao {

    //Т.к. конкурентного доступа не подразумевается, используем обычную мапу
    private final Map<Student, List<TestResult>> resultMap = new HashMap<>();

    @Override
    public void save(TestResult testResult) {
        resultMap.merge(
                testResult.getStudent(),
                List.of(testResult),
                (prev, next) -> Stream.concat(prev.stream(), next.stream()).toList()
        );
    }

    @Override
    public List<TestResult> getAllPreviousResults() {
        return resultMap.values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public List<TestResult> getPreviousResultsForStudent(String firstName, String lastName) {
        return ofNullable(resultMap.get(new Student(firstName, lastName)))
                .orElseGet(Collections::emptyList);
    }
}