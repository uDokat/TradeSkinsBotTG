package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.dokat.entity.AppUserEntity;
import ua.dokat.enums.UserState;
import ua.dokat.service.AddItemHandlerService;

import static ua.dokat.utils.ResponseConfig.*;

@Service
@Log4j
public class AddItemHandlerServiceImpl implements AddItemHandlerService {

    private final AppUserService appUserService;
    private final TraderServiceImpl traderService;
    private final ParserApiServiceImpl parserApiService;

    public AddItemHandlerServiceImpl(AppUserService appUserService, TraderServiceImpl traderService, ParserApiServiceImpl parserApiService) {
        this.appUserService = appUserService;
        this.traderService = traderService;
        this.parserApiService = parserApiService;
    }

    @Override
    public String produce(Update update) {
        User user = update.getMessage().getFrom();
        Long userId = user.getId();
        AppUserEntity appUser = appUserService.findAppUser(user);

        if (!traderService.isRegistered(userId)) return NO_ACCOUNT;
        if (!traderService.isActive(userId)) return NO_ACTIVE;
        if (appUser.getState().equals(UserState.WAIT_ITEM_ID)) return WAIT_ITEM_ID;
        if (!appUser.getState().equals(UserState.BASIC_STATE)) return "Предыдущая команда не завершена.";

        appUser.setState(UserState.WAIT_ITEM_ID);
        appUserService.saveAppUser(appUser);

        return INPUT_ITEM_ID;
    }

    //todo: переделать нахуй все методы на тип String в аргументе itemId и chatId
    //todo: почему-то записываются объекты с статусом еррор
    @Override
    public String addItem(Update update, AppUserEntity appUser, int itemId) {
//        Long userId = update.getMessage().getFrom().getId();
//
//        if (inventoryService.isRecord(userId, itemId)){
//            appUser.setState(UserState.BASIC_STATE);
//            appUserService.saveAppUser(appUser);
//            return ALREADY_ON_LIST;
//        }else {
//            boolean status = inventoryService.addItem(userId, itemId);
//
//            if (!status) return ITEM_NOT_FOUND;
//
//            appUser.setState(UserState.BASIC_STATE);
//            appUserService.saveAppUser(appUser);
//            parserApiService.sendRequestForAddIdToList(String.valueOf(itemId), update.getMessage().getChatId().toString());
//        }
//
//        return ADDED_RESPONSE;
        return "В данный момент команда не работает";
    }
}
