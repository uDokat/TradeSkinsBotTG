package ua.dokat.conrtoller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.utils.MessageUtil;

import javax.annotation.PostConstruct;

@Log4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    private final UpdateController controller;
    private final MessageUtil messageUtil;

    public TelegramBot(UpdateController updateController){
        this.controller = updateController;
        this.messageUtil = new MessageUtil(this);
    }

    @PostConstruct
    public void init(){
        controller.register(this);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        controller.processUpdate(update);
    }

    public void sendMessage(Update update, String textMessage){
        messageUtil.generateAndSendMessage(update, textMessage);
    }

    public void sendMessage(SendMessage sendMessage){
        messageUtil.sendMessage(sendMessage);
    }

    public void sendNotifyMessage(SendPhoto sendPhoto){
        messageUtil.sendNotifyMessage(sendPhoto);
    }
}
