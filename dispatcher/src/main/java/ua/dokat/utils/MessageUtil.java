package ua.dokat.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.dokat.conrtoller.TelegramBot;

@Component
public class MessageUtil {

    private final TelegramBot bot;

    public MessageUtil(TelegramBot bot) {
        this.bot = bot;
    }

    public void generateAndSendMessage(Update update, String textMessage){
        var sendMessage = new SendMessage();
        var message = update.getMessage();

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(textMessage);

        sendMessage(sendMessage);
    }

    public void sendMessage(SendMessage sendMessage){
        if (sendMessage != null){
            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendNotifyMessage(SendPhoto sendPhoto) {
        if (sendPhoto != null){
            try {
                bot.execute(sendPhoto);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
