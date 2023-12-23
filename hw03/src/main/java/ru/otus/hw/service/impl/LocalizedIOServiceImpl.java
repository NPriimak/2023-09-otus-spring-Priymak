package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.LocalizedMessageSupplier;

@RequiredArgsConstructor
@Service
public class LocalizedIOServiceImpl implements LocalizedIOService {

    private final LocalizedMessageSupplier localizedMessageSupplier;

    private final IOService ioService;

    @Override
    public void printLine(String s) {
        ioService.printLine(s);
    }

    @Override
    public void printFormattedLine(String s, Object... args) {
        ioService.printFormattedLine(s, args);
    }

    @Override
    public String readString() {
        return ioService.readString();
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        return ioService.readStringWithPrompt(prompt);
    }

    @Override
    public int readIntForRange(int min, int max, String errorMessage) {
        return ioService.readIntForRange(min, max, errorMessage);
    }

    @Override
    public int readIntForRangeWithPrompt(int min, int max, String prompt, String errorMessage) {
        return ioService.readIntForRangeWithPrompt(min, max, prompt, errorMessage);
    }

    @Override
    public void printLineLocalized(String code) {
        ioService.printLine(localizedMessageSupplier.getMessage(code));
    }

    @Override
    public void printFormattedLineLocalized(String code, Object... args) {
        ioService.printLine(localizedMessageSupplier.getMessage(code, args));
    }

    @Override
    public String readStringWithPromptLocalized(String promptCode) {
        return ioService.readStringWithPrompt(localizedMessageSupplier.getMessage(promptCode));
    }

    @Override
    public int readIntForRangeLocalized(int min, int max, String errorMessageCode) {
        return ioService.readIntForRange(min, max, localizedMessageSupplier.getMessage(errorMessageCode));
    }

    @Override
    public int readIntForRangeWithPromptLocalized(int min, int max, String promptCode, String errorMessageCode) {
        return ioService.readIntForRangeWithPrompt(min, max,
                localizedMessageSupplier.getMessage(promptCode),
                localizedMessageSupplier.getMessage(errorMessageCode)
                );
    }

    @Override
    public String getMessage(String code, Object... args) {
        return localizedMessageSupplier.getMessage(code, args);
    }
}
