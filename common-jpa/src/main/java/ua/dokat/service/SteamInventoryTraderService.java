package ua.dokat.service;

import ua.dokat.entity.TraderEntity;
import ua.dokat.entity.json.buff.BuffSkin;
import ua.dokat.entity.json.buff.TraderInventory;

public interface SteamInventoryTraderService {

    boolean reloadInventory(TraderEntity trader, TraderInventory inventory);
    boolean addSkin(TraderEntity trader, BuffSkin skin);
    boolean removeSkin(TraderEntity trader, BuffSkin skin);

}
