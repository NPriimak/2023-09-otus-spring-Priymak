package ru.otus.hw.dao.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import ru.otus.hw.dao.QuestionDao;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CsvQuestionDaoIntegrationTest {


    @Autowired
    private QuestionDao questionDao;

    @DisplayName("успешно прочитать все вопросы")
    @Test
    void findAllSuccessfullyTest() {
        final var res = assertDoesNotThrow(questionDao::findAll);


        assertAll(
                () -> assertEquals(3, res.size()),
                () -> assertTrue(res.stream().noneMatch(el -> CollectionUtils.isEmpty(el.answers())))
        );
    }
}
