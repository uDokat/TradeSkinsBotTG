package ua.dokat.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface StartHandlerService extends Service{
    SendMessage handle(Update update);
}
