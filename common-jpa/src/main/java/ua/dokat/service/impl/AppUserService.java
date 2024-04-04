package ua.dokat.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.dokat.dao.AppUserDAO;
import ua.dokat.entity.AppUserEntity;
import ua.dokat.enums.UserState;
import ua.dokat.service.AppUser;

import java.util.List;

@Service
public class AppUserService implements AppUser {

    private final AppUserDAO appUserDAO;

    public AppUserService(AppUserDAO appUserDAO) {
        this.appUserDAO = appUserDAO;
    }

    @Override
    public List<AppUserEntity> findAll() {
        return appUserDAO.findAll();
    }

    @Override
    public AppUserEntity findOrSaveAppUser(User telegramUser, String chatId) {
        AppUserEntity appUser = findAppUser(telegramUser);
        if (appUser == null) return createAndSaveAppUser(telegramUser, chatId);
        return appUser;
    }

    @Override
    public AppUserEntity findAppUser(User telegramUser) {
        return appUserDAO.findAppUserByTelegramUserId(telegramUser.getId());
    }

    @Override
    public AppUserEntity createAndSaveAppUser(User telegramUser, String chatId) {
        AppUserEntity appUser = AppUserEntity.builder()
                .telegramUserId(telegramUser.getId())
                .chatId(chatId)
                .userName(telegramUser.getUserName())
                .firstName(telegramUser.getFirstName())
                .lastName(telegramUser.getLastName())
                .isActive(true)
                .state(UserState.BASIC_STATE)
                .build();

        return appUserDAO.save(appUser);
    }

    @Override
    public void saveAppUser(AppUserEntity appUser) {
        appUserDAO.save(appUser);
    }
}
