package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ua.dokat.entity.TraderEntity;
import ua.dokat.entity.json.buff.BuffSkin;
import ua.dokat.entity.json.buff.TraderInventory;
import ua.dokat.repository.SteamInventoryTraderRepository;
import ua.dokat.service.SteamInventoryTraderService;

/**
 *
 *  A service that works with stem's inventory.
 * <p>
 * Used by: handler of the command "/reloadInventory".
 * <p>
 * Returned entity: {@link BuffSkin}
 *
 **/
@Service
@Log4j
public class SteamInventoryTraderServiceImpl implements SteamInventoryTraderService {

    private final SteamInventoryTraderRepository repository;
    private final TraderServiceImpl traderService;

    public SteamInventoryTraderServiceImpl(SteamInventoryTraderRepository repository, TraderServiceImpl traderService) {
        this.repository = repository;
        this.traderService = traderService;
    }

    @Override
    public boolean reloadInventory(TraderEntity trader, TraderInventory inventory) {
        if (!inventory.isValid()) return false;

        trader.setSteamInventory(inventory.getSkins());
        traderService.saveTrader(trader);

        return true;
    }

    @Override
    public boolean addSkin(TraderEntity trader, BuffSkin skin) {
        return repository.addSkin(trader, skin);
    }

    @Override
    public boolean removeSkin(TraderEntity trader, BuffSkin skin) {
        return repository.removeSkin(trader, skin);
    }
}
