package ua.dokat.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.dokat.entity.AppUserEntity;
import ua.dokat.entity.TraderEntity;
import ua.dokat.enums.UserState;
import ua.dokat.service.SetTokenHandlerService;

import static ua.dokat.utils.ResponseConfig.*;

@Service
public class SetTokenHandlerServiceImpl implements SetTokenHandlerService {

    private final TraderServiceImpl traderService;
    private final AppUserService appUserService;

    public SetTokenHandlerServiceImpl(TraderServiceImpl traderService, AppUserService appUserService) {
        this.traderService = traderService;
        this.appUserService = appUserService;
    }

    @Override
    public String produce(Update update) {
        User user = update.getMessage().getFrom();
        AppUserEntity appUser = appUserService.findAppUser(user);

        if (!traderService.isRegistered(user.getId())) return NO_ACCOUNT;
        if (appUser.getState().equals(UserState.WAIT_COOKIE)) return WAIT_TOKEN;
        if (!appUser.getState().equals(UserState.BASIC_STATE)) return ACTION_INC;

        appUser.setState(UserState.WAIT_TOKEN);
        appUserService.saveAppUser(appUser);

        return INPUT_TOKEN;
    }

    @Override
    public String setToken(Update update, AppUserEntity appUser) {
        TraderEntity trader = traderService.findTrader(update.getMessage().getFrom().getId());

        trader.setToken(update.getMessage().getText());
        traderService.saveTrader(trader);

        appUser.setState(UserState.BASIC_STATE);
        appUserService.saveAppUser(appUser);

        return TOKEN_SET;
    }
}
