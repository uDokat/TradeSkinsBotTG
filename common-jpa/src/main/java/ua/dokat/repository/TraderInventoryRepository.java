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
        if (isRecord(trader.getItems(), goods)) return false;

        trader.getItems().add(goods);
        traderService.saveTrader(trader);
        return true;
    }

    public boolean remove(TraderEntity trader, Goods goods){
        if (!isRecord(trader.getItems(), goods)) return false;

        trader.getItems().remove(goods);
        traderService.saveTrader(trader);
        return true;
    }

    public boolean isRecord(List<Goods> goodsList, Goods goods){
        return goodsList.stream()
                .anyMatch(obj -> obj.getData().getId() == goods.getData().getId());
    }
}
