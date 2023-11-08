package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Сервис вывода должен ")
public class StreamsIOServiceTest {

    @Test
    @DisplayName("печатать строку в консоль")
    public void testPrintLine() {
        // Создаем ByteArrayOutputStream для перехвата вывода
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Создаем объект StreamsIOService с перехваченным выводом
        StreamsIOService ioService = new StreamsIOService(printStream);

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
        // Создаем ByteArrayOutputStream для перехвата вывода
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Создаем объект StreamsIOService с перехваченным выводом
        StreamsIOService ioService = new StreamsIOService(printStream);

        // Вызываем метод printFormattedLine
        ioService.printFormattedLine("Ответ: %d", 42);

        // Получаем вывод из ByteArrayOutputStream в виде строки
        String output = outputStream.toString().trim();

        // Проверяем, что вывод соответствует ожидаемому значению
        assertEquals("Ответ: 42", output);
    }
}