package ua.dokat.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.service.MenuHandlerService;

@Service
public class MenuHandlerServiceImpl implements MenuHandlerService {

    @Override
    public String produce(Update update) {
        return "this menu";
    }
}
