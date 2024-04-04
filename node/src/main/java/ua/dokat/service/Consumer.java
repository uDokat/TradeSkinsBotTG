package ua.dokat.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Consumer {
    void consumeTextMessage(Update update);
}
