package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.repository.SkinBuyingRepository;
import ua.dokat.service.SkinBuyingService;

@Service
@Log4j
public class SkinBuyingServiceImpl implements SkinBuyingService {

    private final SkinBuyingRepository repository;

    public SkinBuyingServiceImpl(SkinBuyingRepository repository) {
        this.repository = repository;
    }

    //todo: конфиг
    //todo: должен отправляться запрос на бафф на покупку.
    //todo: как-то мб сделать проверку, был ли куплен айтем в конечном итоге или нет.
    @Override
    public String buy(Update update) {
        if (repository.isEmpty()) return "Скин был куплен ранее. (ID: " + repository.getAndClear() + ")";
        return "Запрос на покупку был отправлен.\nОжидайте подтверждение трейда в стим.";
    }
}
