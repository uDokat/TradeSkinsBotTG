package ua.dokat.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.games.CallbackGame;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ua.dokat.entity.AppUserEntity;
import ua.dokat.service.impl.AppUserService;
import ua.dokat.service.impl.ProducerService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    private final AppUserService service;
    private final ProducerService producerService;

    public TestController(AppUserService service, ProducerService producerService) {
        this.service = service;
        this.producerService = producerService;
    }

    @PostMapping("/api/bot/test/button")
    public String test(){

//        sendEveryone();
        sendEveryoneTest();

        return "test";
    }

    private void sendEveryone(){
        for (AppUserEntity appUser : service.findAll()){
            SendMessage message = new SendMessage();
            message.setChatId(appUser.getChatId());
            message.setText("test message");

            ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
            markup.setResizeKeyboard(true);

            KeyboardButton button = new KeyboardButton("test");
//            button.setText("da");

            KeyboardRow buttons = new KeyboardRow();
            buttons.add(button);

            markup.setKeyboard(List.of(buttons));
//            markup.getKeyboard().add(buttons);

            message.setReplyMarkup(markup);
            producerService.produceAnswer(message);
        }
    }

    private void sendEveryoneTest(){
        for (AppUserEntity appUser : service.findAll()){
            SendMessage sendMessage = new SendMessage(appUser.getChatId(), "test message");

            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            InlineKeyboardButton button = new InlineKeyboardButton("Купить(тест)");

            button.setCallbackData("buy_order_buff");
            markup.setKeyboard(List.of(List.of(button)));

            sendMessage.setReplyMarkup(markup);
            producerService.produceAnswer(sendMessage);
        }
    }
}
