package ua.dokat.repository;

import org.springframework.stereotype.Repository;
import ua.dokat.entity.TraderEntity;
import ua.dokat.entity.json.buff.BuffSkin;
import ua.dokat.service.impl.TraderServiceImpl;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public class SteamInventoryTraderRepository {

    private final TraderServiceImpl traderService;

    public SteamInventoryTraderRepository(TraderServiceImpl traderService) {
        this.traderService = traderService;
    }

    public boolean addSkin(TraderEntity trader, BuffSkin skin){
        List<BuffSkin> buffSkins = trader.getSteamInventory();

        if (isRecord(buffSkins, skin)) return false;

        buffSkins.add(skin);
        trader.setSteamInventory(buffSkins);
        traderService.saveTrader(trader);
        return true;
    }

    public boolean removeSkin(TraderEntity trader, BuffSkin skin){
        List<BuffSkin> buffSkins = trader.getSteamInventory();

        try {

            buffSkins.remove(skin);
            trader.setSteamInventory(buffSkins);
            traderService.saveTrader(trader);
            return true;

        }catch (NullPointerException e){
            return false;
        }
    }

    public boolean isRecord(@NotNull List<BuffSkin> buffSkins, BuffSkin skin){
        return buffSkins.stream()
                .anyMatch(bs -> bs.equals(skin));
    }

    public Optional<BuffSkin> getSkin(TraderEntity trader, int skinId){
        List<BuffSkin> buffSkins = trader.getSteamInventory();

        if (buffSkins != null){
            return buffSkins.stream().
                    filter(sk -> sk.getGoods_id() == skinId)
                    .findFirst();
        }

        return Optional.empty();
    }

    public List<BuffSkin> getSkins(TraderEntity trader){
        return trader.getSteamInventory();
    }
}
