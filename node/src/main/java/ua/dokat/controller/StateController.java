package ua.dokat.controller;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface StateController {
    boolean process(Update update);
}
