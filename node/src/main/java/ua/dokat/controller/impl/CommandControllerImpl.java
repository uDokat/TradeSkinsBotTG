package ua.dokat.controller.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.controller.CommandController;
import ua.dokat.entity.AppUserEntity;
import ua.dokat.service.*;
import ua.dokat.service.impl.AppUserService;
import ua.dokat.service.impl.ProducerService;
import ua.dokat.utils.builder.message.MessageBuilderService;

import static ua.dokat.enums.UserState.BASIC_STATE;
import static ua.dokat.service.enums.ServiceCommands.*;

@Component
@Log4j
public class CommandControllerImpl implements CommandController {

    private final ProducerService producer;
    private final AppUserService appUserService;

    //todo: подумать над переделкой под патерн factory
    private final StartHandlerService startHandler;
    private final MenuHandlerService menuHandler;
    private final RegistrationHandlerService registrationHandler;
    private final ItemsHandlerService itemsHandler;
    private final ReloadInventoryHandlerService reloadInventoryHandler;
    private final SetCookieHandlerService cookieService;
    private final SetTokenHandlerService tokenService;

    private final MessageBuilderService messageBuilder;

    public CommandControllerImpl(ProducerService producer, AppUserService appUserService, StartHandlerService startHandler, MenuHandlerService menuHandler, RegistrationHandlerService registrationHandler, ItemsHandlerService itemsHandler, ReloadInventoryHandlerService reloadInventoryHandler, SetCookieHandlerService cookieService, SetTokenHandlerService tokenService, MessageBuilderService messageBuilder) {
        this.producer = producer;
        this.appUserService = appUserService;
        this.startHandler = startHandler;
        this.menuHandler = menuHandler;
        this.registrationHandler = registrationHandler;
//        this.addItemHandler = addItemHandler;
        this.itemsHandler = itemsHandler;
        this.reloadInventoryHandler = reloadInventoryHandler;
        this.cookieService = cookieService;
        this.tokenService = tokenService;
        this.messageBuilder = messageBuilder;
    }

    @Override
    public boolean process(Update update) {
        String messageText = update.getMessage().getText();
        AppUserEntity user = appUserService.findAppUser(update.getMessage().getFrom());

        if (user.getState() != BASIC_STATE) return false;

        SendMessage message = messageBuilder.buildErrorMessage(user.getChatId());

        if (START.equals(messageText)) {
            message = startHandler.handle(update);
        }

        producer.produce(message);
        return false;

//        else if (MENU.equals(messageText)) {
//            message = menuHandler.produce(update);
//        } else if (REGISTRATION.equals(messageText)){
//            message = registrationHandler.produce(update);
//        } else if (ADD_ITEM.equals(messageText)) {
////            response = addItemHandler.produce(update);
//        } else if (ITEMS.equals(messageText)) {
//            message = itemsHandler.produce(update);
//        }else if (RELOAD_INVENTORY.equals(messageText)){
//            message = reloadInventoryHandler.produce(update);
//        } else if (COOKIE.equals(messageText)) {
//            message = cookieService.produce(update);
//        }else if (TOKEN.equals(messageText)) {
//            message = tokenService.produce(update);
//        }

//        return response.equalsIgnoreCase("") && !appUserService.findAppUser(update.getMessage().getFrom()).getState().equals(BASIC_STATE);
    }
}
