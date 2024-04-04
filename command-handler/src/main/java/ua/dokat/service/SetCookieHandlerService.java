package ua.dokat.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.entity.AppUserEntity;

public interface SetCookieHandlerService extends Service{
    String setCookie(Update update, AppUserEntity appUser);
}
