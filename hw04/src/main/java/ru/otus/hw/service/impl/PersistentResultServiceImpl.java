package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.ResultDao;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.PersistentResultService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersistentResultServiceImpl implements PersistentResultService {

    private final ResultDao resultDao;

    @Override
    public void save(TestResult testResult) {
        resultDao.save(testResult);
    }

    @Override
    public List<TestResult> getAllPreviousResults() {
        return resultDao.getAllPreviousResults();
    }

    @Override
    public List<TestResult> getPreviousResultsForStudent(String firstName,
                                                         String lastName) {
        return resultDao.getPreviousResultsForStudent(firstName, lastName);
    }
}
