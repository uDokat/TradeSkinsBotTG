package ua.dokat.utils.builder.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import ua.dokat.CommandButtonType;

public interface MessageBuilderService {

    SendMessage buildSendMessage(String chatId, String text);

    SendMessage buildSendMessage(String chatId, String text, CommandButtonType... buttonTypes);

    SendMessage buildErrorMessage(String chatId);

    SendPhoto buildSendPhoto(String chatId, String photoUrl);

}
