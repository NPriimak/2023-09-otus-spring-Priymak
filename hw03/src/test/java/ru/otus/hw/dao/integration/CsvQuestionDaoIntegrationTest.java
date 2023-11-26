package ru.otus.hw.dao.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.CollectionUtils;
import ru.otus.hw.base.AbstractSpringBootTest;
import ru.otus.hw.config.AppConfig;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;

import static org.junit.jupiter.api.Assertions.*;


public class CsvQuestionDaoIntegrationTest extends AbstractSpringBootTest {


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
