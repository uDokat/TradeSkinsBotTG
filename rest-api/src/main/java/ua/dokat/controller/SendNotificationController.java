package ua.dokat.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;
import ua.dokat.entity.AppUserEntity;
import ua.dokat.entity.Notify;
import ua.dokat.entity.json.buff.BuffOrder;
import ua.dokat.repository.SkinBuyingRepository;
import ua.dokat.service.impl.AppUserService;
import ua.dokat.service.impl.ProducerService;

@RestController
@Log4j
public class SendNotificationController {

    private final ProducerService producer;
    private final AppUserService appUserService;

    private final SkinBuyingRepository repository;

    public SendNotificationController(ProducerService producer, AppUserService appUserService, SkinBuyingRepository repository) {
        this.producer = producer;
        this.appUserService = appUserService;
        this.repository = repository;
    }

    @PostMapping("api/bot/notify")
    public void send(@RequestParam String skinId){
        log.debug("A notification command has been received.\nFound SkinID: " + skinId);

        repository.add(skinId);

        //todo: добавить поддержку объектов в WebClientUtils. нет смысла делать повторный запрос к апи, ты и так получаешь нужные данные.
//        BuffOrder order = buffApiService.getBuffOrder(skinId);
//        notifyEveryone(order);
    }

    private void notifyEveryone(BuffOrder order){
        String message = "❗❗ Найден скин ❗❗ \n\n" +
                "Цена: " + order.getPrice() + "$\n" +
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
}
