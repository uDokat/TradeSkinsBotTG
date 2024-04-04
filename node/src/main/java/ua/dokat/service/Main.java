package ua.dokat.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Main {
    void produce(Update update);
    Update getRawData();
}
