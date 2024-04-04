package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.service.StartHandlerService;

@Service
@Log4j
public class StartHandlerServiceImpl implements StartHandlerService {

    private String response;

    @Override
    public String produce(Update update) {
        return "response";
    }
}
