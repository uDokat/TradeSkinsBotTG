package ua.dokat.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.entity.InformationForCallbackQuery;
import ua.dokat.entity.Notify;
import ua.dokat.service.Producer;

import static ua.dokat.RabbitQueue.*;

//todo: интерфейс должен содержать service в названии, а класс impl
@Service
public class ProducerService implements Producer {

    private final RabbitTemplate rabbitTemplate;

    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }

    @Override
    public void produceNotify(Notify notify){
        rabbitTemplate.convertAndSend(NOTIFY_ORDER_MESSAGE, notify);
    }

    @Override
    public void produceCallback(Update update){
        rabbitTemplate.convertAndSend(CALLBACK_QUERY, update);
    }

    public void produce(SendMessage sendMessage){
        produceAnswer(sendMessage);
    }

    public void produce(Update update, String textMessage){
        SendMessage sendMessage = createSendMessage(textMessage);
        if (sendMessage == null) return;
        sendMessage.setChatId(update.getMessage().getChatId().toString());

        produceAnswer(sendMessage);
    }

    private SendMessage createSendMessage(String textMessage){
        //todo: zamenit` na exeption
        if (textMessage.equalsIgnoreCase("")) return null;

        var sendMessage = new SendMessage();

        sendMessage.enableMarkdown(true);
        sendMessage.disableWebPagePreview();
        sendMessage.setText(textMessage);
        sendMessage.enableMarkdown(true);

        return sendMessage;
    }
}
