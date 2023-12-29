package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.service.impl.StreamsIOService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Сервис вывода должен ")
public class StreamsIOServiceTest {

    private OutputStream outputStream;
    private StreamsIOService ioService;

    @BeforeEach
    public void initService() {
        // Создаем ByteArrayOutputStream для перехвата вывода
        outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[1024]);
        PrintStream printStream = new PrintStream(outputStream);

        // Создаем объект StreamsIOService с перехваченным выводом
        ioService = new StreamsIOService(printStream, inputStream);
    }

    @Test
    @DisplayName("печатать строку в консоль")
    public void testPrintLine() {


        // Вызываем метод printLine
        ioService.printLine("Привет, Мир!");

        // Получаем вывод из ByteArrayOutputStream в виде строки
        String output = outputStream.toString().trim();

        // Проверяем, что вывод соответствует ожидаемому значению
        assertEquals("Привет, Мир!", output);
    }

    @Test
    @DisplayName("печатать отформатированную строку в консоль")
    public void testPrintFormattedLine() {
        // Вызываем метод printFormattedLine
        ioService.printFormattedLine("Ответ: %d", 42);

        // Получаем вывод из ByteArrayOutputStream в виде строки
        String output = outputStream.toString().trim();

        // Проверяем, что вывод соответствует ожидаемому значению
        assertEquals("Ответ: 42", output);
    }
}