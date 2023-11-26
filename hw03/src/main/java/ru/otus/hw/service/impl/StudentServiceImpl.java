package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.AppConfig;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.LocalizedMessageSupplier;
import ru.otus.hw.service.StudentService;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private static final String ENTER_FIRST_NAME_MESSAGE_KEY = "student.inputFirstName";

    private static final String ENTER_LAST_NAME_MESSAGE_KEY = "student.inputLastName";

    private final IOService ioService;

    private final LocalizedMessageSupplier localizedMessageSupplier;

    @Override
    public Student determineCurrentStudent() {
        final var getNameText = localizedMessageSupplier.getMessage(ENTER_FIRST_NAME_MESSAGE_KEY);
        final var getSurnameText = localizedMessageSupplier.getMessage(ENTER_LAST_NAME_MESSAGE_KEY);
        var firstName = ioService.readStringWithPrompt(getNameText);
        var lastName = ioService.readStringWithPrompt(getSurnameText);
        return new Student(firstName, lastName);
    }
}
