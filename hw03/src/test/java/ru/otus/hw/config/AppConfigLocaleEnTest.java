package ru.otus.hw.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Бин конфигурации должен правильно ")
class AppConfigLocaleEnTest {

    @Autowired
    private AppConfig appConfig;

    @Test
    @DisplayName("читать кол-во необзодимых верных ответ")
    void getRightAnswersCountToPass() {
        assertEquals(3, appConfig.getRightAnswersCountToPass());
    }

    @Test
    @DisplayName("читать имя файла с вопросами на английском")
    void getTestFileNameEn() {
        assertEquals("testQuestions.csv", appConfig.getTestFileName());
    }

}