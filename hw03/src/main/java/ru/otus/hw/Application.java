package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.hw.config.AppConfig;
import ru.otus.hw.service.TestRunnerService;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class Application {

    public static void main(String[] args) {

        final var context = SpringApplication.run(Application.class, args);
        //TODO: Make CLR
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();
    }

}
