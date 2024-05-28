package ua.dokat.controller.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.controller.CallbackQueryController;
import ua.dokat.service.RegistrationHandlerService;
import ua.dokat.service.SetCookieHandlerService;
import ua.dokat.service.SetTokenHandlerService;
import ua.dokat.service.impl.ProducerService;
import ua.dokat.service.impl.SkinBuyingServiceImpl;

import static ua.dokat.CommandButtonType.*;

@Component
@Log4j
public class CallbackQueryControllerImpl implements CallbackQueryController {

    private final SkinBuyingServiceImpl skinBuyingService;
    private final ProducerService producerService;

    //command handlers
    private final RegistrationHandlerService registrationHandlerService;
    private final SetCookieHandlerService setCookieHandlerService;
    private final SetTokenHandlerService setTokenHandlerService;

    public CallbackQueryControllerImpl(SkinBuyingServiceImpl skinBuyingService, ProducerService producerService, RegistrationHandlerService registrationHandlerService, SetCookieHandlerService setCookieHandlerService, SetTokenHandlerService setTokenHandlerService) {
        this.skinBuyingService = skinBuyingService;
        this.producerService = producerService;
        this.registrationHandlerService = registrationHandlerService;
        this.setCookieHandlerService = setCookieHandlerService;
        this.setTokenHandlerService = setTokenHandlerService;
    }

    @Override
    public void process(Update update) {
        var data = update.getCallbackQuery().getData();

        if (REGISTRATION.equals(data)){
            registrationHandlerService.produce(update);
        } else if (SET_COOKIE.equals(data)) {
            setCookieHandlerService.produce(update);
        }else if (SET_TOKEN.equals(data)) {
            setTokenHandlerService.produce(update);
        }
    }
}
