package ru.otus.hw.converter;

import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionConverterTest {

    private final QuestionConverter converter = new QuestionConverter();

    @Test
    public void testConvertToString() {


        Question question = new Question("Is there life on Mars?",
                List.of(
                        new Answer("Science doesn't know this yet", true),
                        new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                        new Answer("Absolutely not", false)
                ));
        final var expectedOutput = """
                1) Is there life on Mars?
                \t• Science doesn't know this yet
                \t• Certainly. The red UFO is from Mars. And green is from Venus
                \t• Absolutely not
                """;

        final var actualOutput = converter.convertToString(question, 1);

        assertEquals(expectedOutput, actualOutput);
    }
}