package ua.dokat.controller.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.dokat.controller.StateController;
import ua.dokat.entity.AppUserEntity;
import ua.dokat.service.AddItemHandlerService;
import ua.dokat.service.SetTokenHandlerService;
import ua.dokat.service.impl.AppUserService;
import ua.dokat.service.impl.ProducerService;
import ua.dokat.service.impl.SetCookieHandlerServiceImpl;

import static ua.dokat.enums.UserState.*;

@Component
@Log4j
public class StateControllerImpl implements StateController {

    private final AppUserService appUserService;
    private final ProducerService producer;
    private final AddItemHandlerService addItemHandler;
    private final SetCookieHandlerServiceImpl cookieHandler;
    private final SetTokenHandlerService tokenService;

    public StateControllerImpl(AppUserService appUserService, ProducerService producer, AddItemHandlerService addItemHandler, SetCookieHandlerServiceImpl cookieHandler, SetTokenHandlerService tokenService) {
        this.appUserService = appUserService;
        this.producer = producer;
        this.addItemHandler = addItemHandler;
        this.cookieHandler = cookieHandler;
        this.tokenService = tokenService;
    }

    @Override
    public boolean process(Update update) {
        User user = update.getMessage().getFrom();
        AppUserEntity appUser = appUserService.findAppUser(user);

        String response = "";

        if (WAIT_ITEM_ID.equals(appUser.getState())){
            response = processWaitItemIdState(update, appUser);
        } else if (WAIT_COOKIE.equals(appUser.getState())) {
            response = processWaitCookieState(update, appUser);
        } else if (WAIT_TOKEN.equals(appUser.getState())) {
            response = processWaitTokenState(update, appUser);
        }

        producer.produce(update, response);
        return response.equalsIgnoreCase("");
    }

    private String processWaitItemIdState(Update update, AppUserEntity appUser) {
        int itemId;

        try {

            itemId = Integer.parseInt(update.getMessage().getText());

        }catch (NumberFormatException e){

            log.error("NumberFormatException " + e);

            appUser.setState(BASIC_STATE);
            appUserService.saveAppUser(appUser);

            return "Id был введён с применением символов";

        }

        return addItemHandler.addItem(update, appUser, itemId);
    }

    private String processWaitCookieState(Update update, AppUserEntity appUser){
        return cookieHandler.setCookie(update, appUser);
    }

    private String processWaitTokenState(Update update, AppUserEntity appUser){
        return tokenService.setToken(update, appUser);
    }
}
