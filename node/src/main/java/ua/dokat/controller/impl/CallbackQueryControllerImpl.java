package ua.dokat.controller.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.controller.CallbackQueryController;
import ua.dokat.service.impl.ProducerService;
import ua.dokat.service.impl.SkinBuyingServiceImpl;

import static ua.dokat.enums.ButtonType.BUY_ORDER_BUFF;

@Component
@Log4j
public class CallbackQueryControllerImpl implements CallbackQueryController {

    private final SkinBuyingServiceImpl skinBuyingService;
    private final ProducerService producerService;

    public CallbackQueryControllerImpl(SkinBuyingServiceImpl skinBuyingService, ProducerService producerService) {
        this.skinBuyingService = skinBuyingService;
        this.producerService = producerService;
    }

    @Override
    public void process(Update update) {
        var data = update.getCallbackQuery().getData();

        if (BUY_ORDER_BUFF.equals(data)){
            producerService.produce(update, skinBuyingService.buy(update));
        }
    }
}
