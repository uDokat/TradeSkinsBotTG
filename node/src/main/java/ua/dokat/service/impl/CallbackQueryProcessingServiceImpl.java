package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.controller.impl.CallbackQueryControllerImpl;
import ua.dokat.service.CallbackQueryProcessingService;

import static ua.dokat.RabbitQueue.CALLBACK_QUERY;

@Service
@Log4j
public class CallbackQueryProcessingServiceImpl implements CallbackQueryProcessingService {

    private final CallbackQueryControllerImpl queryController;

    public CallbackQueryProcessingServiceImpl(CallbackQueryControllerImpl queryController) {
        this.queryController = queryController;
    }

    @Override
    @RabbitListener(queues = CALLBACK_QUERY)
    public void processingCallbackQuery(Update update) {
        queryController.process(update);
    }
}
