package ua.dokat.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.entity.AppUserEntity;

public interface SetTokenHandlerService extends Service{
    String setToken(Update update, AppUserEntity appUser);
}
