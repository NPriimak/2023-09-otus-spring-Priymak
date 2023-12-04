package ru.otus.hw.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.LocalizedMessageSupplier;
import ru.otus.hw.service.StudentRegistrationFacade;

@Service
public class StudentRegistrationFacadeIOImpl implements StudentRegistrationFacade {

    private static final String ENTER_FIRST_NAME_MESSAGE_KEY = "student.inputFirstName";

    private static final String ENTER_LAST_NAME_MESSAGE_KEY = "student.inputLastName";

    private final IOService ioService;

    private final String firstNameQuestion;

    private final String lastNameQuestion;

    public StudentRegistrationFacadeIOImpl(IOService ioService,
                                           LocalizedMessageSupplier localizedMessageSupplier) {
        this.ioService = ioService;
        this.firstNameQuestion = localizedMessageSupplier.getMessage(ENTER_FIRST_NAME_MESSAGE_KEY);
        this.lastNameQuestion = localizedMessageSupplier.getMessage(ENTER_LAST_NAME_MESSAGE_KEY);
    }


    @Override
    public String askFirstName() {
        return ioService.readStringWithPrompt(firstNameQuestion);
    }

    @Override
    public String askLastName() {
        return ioService.readStringWithPrompt(lastNameQuestion);
    }
}
