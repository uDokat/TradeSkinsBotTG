package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.dokat.service.RegistrationHandlerService;

import static ua.dokat.utils.ResponseConfig.*;

@Service
@Log4j
public class RegistrationHandlerServiceImpl implements RegistrationHandlerService {

    private final TraderServiceImpl traderServiceImpl;

    public RegistrationHandlerServiceImpl(TraderServiceImpl traderServiceImpl) {
        this.traderServiceImpl = traderServiceImpl;
    }

    @Override
    public String produce(Update update) {
        User user = update.getMessage().getFrom();

        if (traderServiceImpl.isRegistered(user.getId())) return ALREADY_REGISTERED;

        traderServiceImpl.createAndSaveTrader(user.getId());

        return GOOD_REGISTRATION;
    }
}
