package ua.dokat.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ua.dokat.conrtoller.UpdateController;
import ua.dokat.entity.Notify;
import ua.dokat.service.AnswerConsumer;

import static ua.dokat.RabbitQueue.ANSWER_MESSAGE;
import static ua.dokat.RabbitQueue.NOTIFY_ORDER_MESSAGE;

@Service
public class AnswerConsumerService implements AnswerConsumer {

    private final UpdateController controller;

    public AnswerConsumerService(UpdateController controller) {
        this.controller = controller;
    }

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consumeAnswerMessage(SendMessage sendMessage) {
        controller.sendAnswerMessage(sendMessage);
    }

    //todo: наверное должно быть в отдельном классе так как consumerservice имеет свой @rabbatlistener исходя из этого можно сделать вывод что это класс для типа сообщений answer исходя из исходя можно сделать вывод что это должно быть в отдельном классе отвечающий за обработку уведомлений
    @Override
    @RabbitListener(queues = NOTIFY_ORDER_MESSAGE)
    public void consumeNotifyMessage(Notify notify) {
        controller.sendNotifyMessage(notify);
    }
}
