package ua.dokat.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ua.dokat.entity.AppUserEntity;

public interface TraderInventoryHandlerService extends Service{
    String addItem(AppUserEntity appUser, int skinId);
    String removeItem(AppUserEntity appUser, int skinId);
    String clearItems(AppUserEntity appUser);
}
