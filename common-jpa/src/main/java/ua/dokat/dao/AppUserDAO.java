package ua.dokat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dokat.entity.AppUserEntity;

import java.util.List;

public interface AppUserDAO extends JpaRepository<AppUserEntity, Long> {
    AppUserEntity findAppUserByTelegramUserId(Long id);
    List<AppUserEntity> findAll();
}
