package ru.otus.hw.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import ru.otus.hw.exceptions.QuestionFileNotFoundException;

import java.util.Locale;
import java.util.Map;

import static java.util.Optional.ofNullable;

@ConfigurationProperties(prefix = "test")
public class AppConfig implements TestConfig, TestFileNameProvider {

    private final int rightAnswersCountToPass;

    private final Map<Locale, String> questionsFiles;

    private final Locale locale;

    @ConstructorBinding
    public AppConfig(int rightAnswersCountToPass,
                     Map<Locale, String> questionsFiles,
                     Locale locale) {
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.questionsFiles = questionsFiles;
        this.locale = locale;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public String getTestFileName() {
        return ofNullable(questionsFiles.get(locale))
                .orElseThrow(() -> new QuestionFileNotFoundException("Can't find questions file for locale: " + locale));
    }
}
