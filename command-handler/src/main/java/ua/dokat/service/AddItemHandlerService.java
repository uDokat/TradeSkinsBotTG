package ua.dokat.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.entity.AppUserEntity;

public interface AddItemHandlerService extends Service{
    String addItem(Update update, AppUserEntity appUser, int itemId);
}
