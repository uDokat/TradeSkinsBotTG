package ua.dokat.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import ua.dokat.entity.AppUserEntity;
import ua.dokat.entity.Notify;
import ua.dokat.entity.json.Entity;
import ua.dokat.entity.json.buff.BuffOrder;
import ua.dokat.entity.json.http.HttpRequestInfo;
import ua.dokat.repository.SkinBuyingRepository;
import ua.dokat.service.impl.AppUserService;
import ua.dokat.service.impl.ProducerService;
import ua.dokat.utils.webflux.WebClientUtils;

@RestController
@Log4j
@PropertySource("classpath:api.properties")
public class SendNotificationController {

    private final ProducerService producer;
    private final AppUserService appUserService;

    private final SkinBuyingRepository repository;

    private final WebClientUtils webClientUtils;

    @Value("${ext.api.url}")
    private String url;
    @Value("${ext.api.buff.params.order}")
    private String params;

    public SendNotificationController(ProducerService producer, AppUserService appUserService, SkinBuyingRepository repository, WebClientUtils webClientUtils) {
        this.producer = producer;
        this.appUserService = appUserService;
        this.repository = repository;
        this.webClientUtils = webClientUtils;
    }

    @PostMapping("api/bot/notify")
    public void send(@RequestParam String skinId){
        log.info("A notification command has been received.\nFound SkinID: " + skinId);

        repository.add(skinId);

        Entity<BuffOrder> buffOrder = webClientUtils.sendGetAndGetResponse(new HttpRequestInfo(url, buildParams(params, skinId)), BuffOrder.class);
        if (!buffOrder.isValid()) {
            log.error("Order is not valid");
            return;
        }
        notifyEveryone(buffOrder.getEntityObject());
    }

    private void notifyEveryone(BuffOrder order){
        String message = "❗❗ Найден скин ❗❗ \n\n" +
                "Цена: " + order.getOrders().get(0).getPrice() + "$\n" +
                "Ссылка: " + order.getUrl();

        //todo: список юзеров должен подтягиваться с бд buffparserdb
        for (AppUserEntity appUser : appUserService.findAll()){
            Notify notify = buildNotify(order, appUser.getChatId(), message);
            producer.produceNotify(notify);
        }
    }

    //todo: сделать сервис NotifyService
    private Notify buildNotify(BuffOrder order, String chatId, String message){
        Notify notify = new Notify();
        notify.setOrder(order);
        notify.setChatId(chatId);
        notify.setMessage(message);
        return notify;
    }

    private String buildParams(String params, String skinId){
        return String.format(params, skinId, "3");
    }
}
