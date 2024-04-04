package ua.dokat.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.dokat.entity.AppUserEntity;

import java.util.List;

public interface AppUser {
    List<AppUserEntity> findAll();
    AppUserEntity findOrSaveAppUser(User telegramUser, String chatId);
    AppUserEntity findAppUser(User telegramUser);
    AppUserEntity createAndSaveAppUser(User telegramUser, String chatId);
    void saveAppUser(AppUserEntity appUser);
}
