package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.controller.impl.CommandControllerImpl;
import ua.dokat.controller.impl.StateControllerImpl;
import ua.dokat.service.*;

import static ua.dokat.RabbitQueue.*;

//todo: интерфейс должен содержать service в названии, а класс impl
@Service
@Log4j
public class ConsumerService implements Consumer {

    private final AppUserService appUserService;

    private final CommandControllerImpl commandController;
    private final StateControllerImpl stateController;

    public ConsumerService(AppUserService appUserService, CommandControllerImpl commandController, StateControllerImpl stateController) {
        this.appUserService = appUserService;
        this.commandController = commandController;
        this.stateController = stateController;
    }

    @Override
    @RabbitListener(queues = TEXT_MESSAGE)
    public void consumeTextMessage(Update update) {
        appUserService.findOrSaveAppUser(update.getMessage().getFrom(), update.getMessage().getChatId().toString());
        if (!commandController.process(update)) return;
        if (!stateController.process(update));
    }
}
