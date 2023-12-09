package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.LocaleConfig;
import ru.otus.hw.service.LocalizedMessageSupplier;

@Service
@RequiredArgsConstructor
@Primary
public class LocalizedMessageSupplierImpl implements LocalizedMessageSupplier {

    private final MessageSource messageSource;

    private final LocaleConfig localeConfig;

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, localeConfig.getLocale());
    }
}
