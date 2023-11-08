package ru.otus.hw.config.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.otus.hw.config.AppConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(AppConfig.class)
@TestPropertySource(value = "classpath:test.properties")
@DisplayName("Бин конфигурации должен правильно ")
class AppConfigTest {

    @Autowired
    private AppConfig appConfig;

    @Test
    @DisplayName("читать кол-во необзодимых верных ответ")
    void getRightAnswersCountToPass() {
        assertEquals(3, appConfig.getRightAnswersCountToPass());
    }

    @Test
    @DisplayName("читать имя файла с вопросами")
    void getTestFileName() {
        assertEquals("testQuestions.csv", appConfig.getTestFileName());
    }

}