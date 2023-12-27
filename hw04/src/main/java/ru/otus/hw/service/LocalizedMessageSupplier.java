package ru.otus.hw.service;

public interface LocalizedMessageSupplier {

    /**
     * Method for retrieving a localized message
     * @param key the key
     * @param args arguments for placeholders
     * @return the localized message
     * @throws org.springframework.context.NoSuchMessageException if the message is not found for the given key
     */
    String getMessage(String key, Object... args) throws org.springframework.context.NoSuchMessageException;
}
