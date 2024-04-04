package ua.dokat.controller;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackQueryController {
    void process(Update update);
}
