package ua.dokat.controller;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandController {
    boolean process(Update update);
}
