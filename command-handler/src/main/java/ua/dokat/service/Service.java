package ua.dokat.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Service {
    String produce(Update update);
}
