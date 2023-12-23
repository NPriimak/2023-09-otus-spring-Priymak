package ru.otus.hw.service;

public interface LocalizedIOService extends LocalizedMessageSupplier, IOService {
    void printLineLocalized(String code);

    void printFormattedLineLocalized(String code, Object ...args);

    String readStringWithPromptLocalized(String promptCode);

    int readIntForRangeLocalized(int min, int max, String errorMessageCode);

    int readIntForRangeWithPromptLocalized(int min, int max, String promptCode, String errorMessageCode);
}
