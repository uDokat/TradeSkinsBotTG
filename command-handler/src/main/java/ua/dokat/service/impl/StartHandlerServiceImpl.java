package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.CommandButtonType;
import ua.dokat.service.StartHandlerService;
import ua.dokat.utils.builder.message.MessageBuilderService;

import static ua.dokat.CommandButtonType.*;

@Service
@Log4j
public class StartHandlerServiceImpl implements StartHandlerService {

    //TODO: сделать через конфиг
    private String response = "response";
    private SendMessage message = null;

    private final MessageBuilderService messageBuilderService;

    public StartHandlerServiceImpl(MessageBuilderService messageBuilderService) {
        this.messageBuilderService = messageBuilderService;
    }

    @Override
    public String produce(Update update) {
        return response;
    }

    @Override
    public SendMessage handle(Update update) {

        if (message == null){
            String chatId = update.getMessage().getChatId().toString();
            SendMessage message = messageBuilderService.buildSendMessage(chatId, response, REGISTRATION, SET_COOKIE, REGISTRATION);

            return message;
        }else {
            message.setChatId(update.getMessage().getChatId().toString());
            return message;
        }
    }
}