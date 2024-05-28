package ua.dokat.conrtoller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.dokat.RabbitQueue;
import ua.dokat.entity.Notify;
import ua.dokat.entity.json.buff.BuffOrder;
import ua.dokat.service.UpdateProducer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Log4j
@Component
public class UpdateController {

    private TelegramBot bot;
    private final UpdateProducer producer;

    public UpdateController(UpdateProducer producer) {
        this.producer = producer;
    }

    public void processUpdate(Update update){
        if (update.hasMessage()) distributeMessageByType(update);
        if (update.hasCallbackQuery()) distributeButtonCallbackQueryByType(update);
    }

    private void distributeMessageByType(Update update){
        var message = update.getMessage();

        if (message.getText() != null){
            processTextMessage(update);
        }
    }

    private void distributeButtonCallbackQueryByType(Update update){
        var callbackQuery = update.getCallbackQuery();

        if (callbackQuery.getData() != null){
            processCallBackQuery(update);
        }
    }

    private void processTextMessage(Update update){
        producer.produce(RabbitQueue.TEXT_MESSAGE, update);
    }

    private void processCallBackQuery(Update update){
        producer.produce(RabbitQueue.CALLBACK_QUERY, update);
    }

    public void sendAnswerMessage(SendMessage sendMessage){
        bot.sendMessage(sendMessage);
    }

    //todo: сделать метод buildSendPhoto в классе MessageBuilderService и удалить отсюда инициализацию SendPhoto. Тут должна быть онли отправка
    @Deprecated
    public void sendNotifyMessage(Notify notify){
        InputFile image = getImage(notify.getOrder());

        if (image == null) return;

        var sendPhoto = new SendPhoto();
        sendPhoto.setChatId(notify.getChatId());
        sendPhoto.setCaption(notify.getMessage());
        sendPhoto.setPhoto(image);
        sendPhoto.setReplyMarkup(buildKeyboard());

        bot.sendNotifyMessage(sendPhoto);
    }

    @Deprecated
    private InlineKeyboardMarkup buildKeyboard(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton button = new InlineKeyboardButton("Купить(тест)");

        button.setCallbackData("buy_order_buff");
        markup.setKeyboard(List.of(List.of(button)));

        return markup;
    }

    //todo: вынести куда-то
    @Deprecated
    private InputFile getImage(BuffOrder order){
        String url = order.getOrders().get(0).getInspect_url();

        try {

            InputStream stream;

            if (url == null || url.equalsIgnoreCase("")){
                stream = new URL(order.getOrders().get(0).getIcon_url()).openStream();
            }else {
                stream = new URL(url).openStream();
            }

            return new InputFile(stream, "test.jpg");
        } catch (IOException e) {
            log.error("Какой-то нахуй spec равен null", e);
            return null;
        }
    }

    public void register(TelegramBot bot){
        this.bot = bot;
    }
}
