package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.dokat.entity.json.Goods;
import ua.dokat.service.ItemsHandlerService;

import java.util.List;

import static ua.dokat.utils.ResponseConfig.*;

@Service
@Log4j
public class ItemsHandlerServiceImpl implements ItemsHandlerService {

    private final TraderServiceImpl traderServiceImpl;

    public ItemsHandlerServiceImpl(TraderServiceImpl traderServiceImpl) {
        this.traderServiceImpl = traderServiceImpl;
    }

    @Override
    public String produce(Update update) {
//        User user = update.getMessage().getFrom();
//        if (!traderServiceImpl.isRegistered(user.getId())) return NO_ACCOUNT;
//
//        List<Goods> items = inventoryService.findItems(user.getId());
//        if (items.isEmpty()) return DONT_HAVE_LIST;
//
//        return ITEMS_LIST + "\n\n" + buildItemList(items);
        return "В данный момент команда не работает";
    }

    private String buildItemList(List<Goods> items){
        StringBuilder builder = new StringBuilder();

        for (Goods item : items) {
            Goods.GoodsData data = item.getData();

            builder.append(data.getName()).append(" (").append(data.getId()).append(")").append("\n")
                    .append(data.getUrl()).append("\n\n");
        }

        return builder.toString();
    }
}
