package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private static final String ENTER_FIRST_NAME_MESSAGE_KEY = "student.inputFirstName";

    private static final String ENTER_LAST_NAME_MESSAGE_KEY = "student.inputLastName";

    private final LocalizedIOService localizedIOService;

    @Override
    public Student determineCurrentStudent() {
        var firstName = localizedIOService.readStringWithPromptLocalized(ENTER_FIRST_NAME_MESSAGE_KEY);
        var lastName = localizedIOService.readStringWithPromptLocalized(ENTER_LAST_NAME_MESSAGE_KEY);
        return new Student(firstName, lastName);
    }
}
