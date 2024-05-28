package ua.dokat.repository;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import ua.dokat.entity.TraderEntity;
import ua.dokat.entity.json.Goods;
import ua.dokat.service.impl.TraderServiceImpl;

import java.util.List;

@Repository
@Log4j
public class TraderInventoryRepository {

    private final TraderServiceImpl traderService;

    public TraderInventoryRepository(TraderServiceImpl traderService) {
        this.traderService = traderService;
    }

    public boolean add(TraderEntity trader, Goods goods){
        if (isRecord(trader.getItems(), goods.getData().getId())) return false;

        trader.getItems().add(goods);
        traderService.saveTrader(trader);
        return true;
    }

    public boolean remove(TraderEntity trader, int skinId){
        if (!isRecord(trader.getItems(), skinId)) return false;

        trader.getItems().removeIf(skin -> skin.getData().getId() == skinId);
        traderService.saveTrader(trader);
        return true;
    }

    public boolean clear(TraderEntity trader){
        trader.getItems().clear();
        traderService.saveTrader(trader);
        return true;
    }

    public boolean isRecord(List<Goods> goodsList, int skinId){
        return goodsList.stream()
                .anyMatch(obj -> obj.getData().getId() == skinId);
    }
}
