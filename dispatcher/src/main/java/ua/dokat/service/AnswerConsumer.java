package ua.dokat.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ua.dokat.entity.Notify;

public interface AnswerConsumer {
    void consumeAnswerMessage(SendMessage sendMessage);
    void consumeNotifyMessage(Notify notify);
}
