package ua.dokat.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.dokat.entity.AppUserEntity;
import ua.dokat.entity.TraderEntity;
import ua.dokat.entity.json.Entity;
import ua.dokat.entity.json.buff.TraderInventory;
import ua.dokat.enums.UserState;
import ua.dokat.service.ReloadInventoryHandlerService;
import ua.dokat.utils.webflux.WebClientUtils;

import static ua.dokat.utils.ResponseConfig.*;

/**
 *
 * Service handler for the command "/reload_inventory"
 *
 **/
@Service
public class ReloadInventoryHandlerServiceImpl implements ReloadInventoryHandlerService {

    private final SteamInventoryTraderServiceImpl inventoryService;
    private final AppUserService appUserService;
    private final TraderServiceImpl traderService;

    private final WebClientUtils webClientUtils;

    public ReloadInventoryHandlerServiceImpl(SteamInventoryTraderServiceImpl inventoryService, AppUserService appUserService, TraderServiceImpl traderService, WebClientUtils webClientUtils) {
        this.inventoryService = inventoryService;
        this.appUserService = appUserService;
        this.traderService = traderService;
        this.webClientUtils = webClientUtils;
    }

    /**
     * A method of processing a command. Reloads the user's inventory.
     *
     * @param update {@link Update}
     *
     * @return String - returns a completed response
     **/
    @Override
    public String produce(Update update) {
        User user = update.getMessage().getFrom();
        Long userId = user.getId();

        AppUserEntity appUser = appUserService.findAppUser(user);
        TraderEntity trader = traderService.findTrader(userId);

        if (!traderService.isRegistered(userId)) return NO_ACCOUNT;
        if (!traderService.isActive(userId)) return NO_ACTIVE;
        if (!appUser.getState().equals(UserState.BASIC_STATE)) return ACTION_INC;

        Entity<TraderInventory> inventory = webClientUtils.sendGetAndGetResponse(WebClientUtils.TO, trader.toInformation(), TraderInventory.class);
        if (!inventory.isValid() && !inventoryService.reloadInventory(trader, inventory.getEntityObject())) return ERROR_RELOAD;

        return RELOADED;
    }
}