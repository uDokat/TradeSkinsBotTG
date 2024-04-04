package ua.dokat.controller.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.controller.CommandController;
import ua.dokat.service.*;
import ua.dokat.service.impl.AppUserService;
import ua.dokat.service.impl.ProducerService;

import static ua.dokat.enums.UserState.BASIC_STATE;
import static ua.dokat.service.enums.ServiceCommands.*;

@Component
@Log4j
public class CommandControllerImpl implements CommandController {

    private final ProducerService producer;
    private final AppUserService appUserService;

    private final StartHandlerService startHandler;
    private final MenuHandlerService menuHandler;
    private final RegistrationHandlerService registrationHandler;
    private final AddItemHandlerService addItemHandler;
    private final ItemsHandlerService itemsHandler;
    private final ReloadInventoryHandlerService reloadInventoryHandler;
    private final SetCookieHandlerService cookieService;
    private final SetTokenHandlerService tokenService;

    public CommandControllerImpl(ProducerService producer, AppUserService appUserService, StartHandlerService startHandler, MenuHandlerService menuHandler, RegistrationHandlerService registrationHandler, AddItemHandlerService addItemHandler, ItemsHandlerService itemsHandler, ReloadInventoryHandlerService reloadInventoryHandler, SetCookieHandlerService cookieService, SetTokenHandlerService tokenService) {
        this.producer = producer;
        this.appUserService = appUserService;
        this.startHandler = startHandler;
        this.menuHandler = menuHandler;
        this.registrationHandler = registrationHandler;
        this.addItemHandler = addItemHandler;
        this.itemsHandler = itemsHandler;
        this.reloadInventoryHandler = reloadInventoryHandler;
        this.cookieService = cookieService;
        this.tokenService = tokenService;
    }

    @Override
    public boolean process(Update update) {
        String response = "";
        String messageText = update.getMessage().getText();

        if (START.equals(messageText)){
            response = startHandler.produce(update);
        } else if (MENU.equals(messageText)) {
            response = menuHandler.produce(update);
        } else if (REGISTRATION.equals(messageText)){
            response = registrationHandler.produce(update);
        } else if (ADD_ITEM.equals(messageText)) {
            response = addItemHandler.produce(update);
        } else if (ITEMS.equals(messageText)) {
            response = itemsHandler.produce(update);
        }else if (RELOAD_INVENTORY.equals(messageText)){
            response = reloadInventoryHandler.produce(update);
        } else if (COOKIE.equals(messageText)) {
            response = cookieService.produce(update);
        }else if (TOKEN.equals(messageText)){
            response = tokenService.produce(update);
        }

        producer.produce(update, response);
        return response.equalsIgnoreCase("") && !appUserService.findAppUser(update.getMessage().getFrom()).getState().equals(BASIC_STATE);
    }
}
