package ru.otus.hw.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = "test.locale=ru_RU")
@DisplayName("Бин конфигурации должен правильно ")
class AppConfigLocaleRuTest {

    @Autowired
    private AppConfig appConfig;

    @Test
    @DisplayName("читать имя файла с вопросами на русском")
    void getTestFileNameRu() {
        assertEquals("testQuestions_ru.csv", appConfig.getTestFileName());
    }

}