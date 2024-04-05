package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.dokat.entity.AppUserEntity;
import ua.dokat.entity.TraderEntity;
import ua.dokat.entity.json.Entity;
import ua.dokat.entity.json.Goods;
import ua.dokat.entity.json.http.HttpRequestInfo;
import ua.dokat.enums.UserState;
import ua.dokat.repository.TraderInventoryRepository;
import ua.dokat.service.AddItemHandlerService;
import ua.dokat.utils.webflux.WebClientUtils;

import static ua.dokat.utils.ResponseConfig.*;

/**
 *
 * Service handler for the command "/reload_inventory"
 *
 **/
@Service
@Log4j
public class AddItemHandlerServiceImpl implements AddItemHandlerService {

    private final AppUserService appUserService;
    private final TraderServiceImpl traderService;
    //todo: заменить на WebClientUtils
    private final ParserApiServiceImpl parserApiService;

    private final TraderInventoryRepository inventoryRepository;

    private final WebClientUtils webClientUtils;

    public AddItemHandlerServiceImpl(AppUserService appUserService, TraderServiceImpl traderService, ParserApiServiceImpl parserApiService, TraderInventoryRepository inventoryRepository, WebClientUtils webClientUtils) {
        this.appUserService = appUserService;
        this.traderService = traderService;
        this.parserApiService = parserApiService;
        this.inventoryRepository = inventoryRepository;
        this.webClientUtils = webClientUtils;
    }

    /**
     *
     * A method of processing a command. Sets the wait state of WAIT_ITEM_ID.
     * <p>
     * The command is canceled if {@link UserState} is not equal to BASIC_STATE.
     *
     * @param update {@link Update}
     * @return {@link String} - returns a completed response
     *
     **/
    @Override
    public String produce(Update update) {
        User user = update.getMessage().getFrom();
        Long userId = user.getId();
        AppUserEntity appUser = appUserService.findAppUser(user);

        if (!traderService.isRegistered(userId)) return NO_ACCOUNT;
        if (!traderService.isActive(userId)) return NO_ACTIVE;
        if (appUser.getState().equals(UserState.WAIT_ITEM_ID)) return WAIT_ITEM_ID;
        if (!appUser.getState().equals(UserState.BASIC_STATE)) return ACTION_INC;

        appUser.setState(UserState.WAIT_ITEM_ID);
        appUserService.saveAppUser(appUser);

        return INPUT_ITEM_ID;
    }

    /**
     *
     * Adds the skin's id to the {@link TraderEntity} entity list.
     * <p>
     * The method will be canceled if {@link UserState} is not equal to BASIC_STATE or if the specified idi was not found on the site.
     *
     * @param update {@link Update}
     * @param appUser {@link AppUserEntity}
     * @param skinId Used to search for a skin on the site.
     * @return {@link String} - returns a completed response
     **/
    //todo: почему-то записываются объекты с статусом еррор
    @Override
    public String addItem(Update update, AppUserEntity appUser, int skinId) {

        Long userId = appUser.getTelegramUserId();
        String chatId = appUser.getChatId();
        TraderEntity trader = traderService.findTrader(userId);
        Entity<Goods> goods = webClientUtils.sendGetAndGetResponse(new HttpRequestInfo("http://localhost:10001", "/api/buff/item?itemId=" + skinId), Goods.class);

        if (!goods.isValid()) return ITEM_NOT_FOUND;

        appUser.setState(UserState.BASIC_STATE);
        appUserService.saveAppUser(appUser);

        if (!inventoryRepository.add(trader, goods.getEntityObject())){
            return ALREADY_ON_LIST;
        }else {
            parserApiService.sendRequestForAddIdToList(String.valueOf(skinId), chatId);
            return ADDED_RESPONSE;
        }
    }
}
