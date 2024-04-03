package ua.dokat.entity;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Getter
@Setter
public class InformationForCallbackQuery {

    private SendMessage sendMessage;
    private String skinId;

}
