package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.AppConfig;
import ru.otus.hw.service.LocalizedMessageSupplier;

@Service
@RequiredArgsConstructor
public class LocalizedMessageSupplierImpl implements LocalizedMessageSupplier {

    private final MessageSource messageSource;

    private final AppConfig appConfig;

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, appConfig.getLocale());
    }
}
