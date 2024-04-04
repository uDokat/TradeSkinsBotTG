package ua.dokat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dokat.entity.TraderEntity;

public interface TraderDAO extends JpaRepository<TraderEntity, Long> {
    TraderEntity findByTelegramUserId(Long id);
}
