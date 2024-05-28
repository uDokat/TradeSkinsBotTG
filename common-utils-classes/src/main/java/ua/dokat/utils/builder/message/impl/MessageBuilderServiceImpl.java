package ua.dokat.utils.builder.message.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.dokat.CommandButtonType;
import ua.dokat.utils.builder.message.MessageBuilderService;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageBuilderServiceImpl implements MessageBuilderService {

    @Override
    public SendMessage buildSendMessage(String chatId, String text) {

        //todo: zamenit` na exeption
        if (text.equalsIgnoreCase("")) return null;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.enableMarkdown(true);
        sendMessage.disableWebPagePreview();

        return sendMessage;
    }

    @Override
    public SendMessage buildSendMessage(String chatId, String text, CommandButtonType... buttonTypes) {

        SendMessage sendMessage = buildSendMessage(chatId, text);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> buttons = new ArrayList<>();

        for (CommandButtonType buttonType : buttonTypes) {
            InlineKeyboardButton button = new InlineKeyboardButton(buttonType.getText());
            button.setCallbackData(buttonType.toString());
            buttons.add(button);
        }

        markup.setKeyboard(List.of(buttons));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    @Override
    public SendMessage buildErrorMessage(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Something went wrong");
        return message;
    }

    @Override
    public SendPhoto buildSendPhoto(String chatId, String photoUrl) {
        return null;
    }
}
