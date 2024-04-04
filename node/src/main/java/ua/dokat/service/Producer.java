package ua.dokat.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.entity.InformationForCallbackQuery;
import ua.dokat.entity.Notify;

public interface Producer {
    void produceAnswer(SendMessage sendMessage);
    void produceNotify(Notify notify);
    void produceCallback(Update update);
}
