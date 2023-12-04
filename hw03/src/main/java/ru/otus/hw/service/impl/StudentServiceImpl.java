package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.StudentRegistrationFacade;
import ru.otus.hw.service.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

   private final StudentRegistrationFacade studentRegistrationFacade;

    @Override
    public Student determineCurrentStudent() {
        var firstName = studentRegistrationFacade.askFirstName();
        var lastName = studentRegistrationFacade.askLastName();
        return new Student(firstName, lastName);
    }
}
