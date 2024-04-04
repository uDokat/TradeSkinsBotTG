package ua.dokat.service;

import ua.dokat.entity.TraderEntity;

public interface TraderService {
    TraderEntity createAndSaveTrader(Long id);
    TraderEntity findTrader(Long id);
    TraderEntity saveTrader(TraderEntity entity);
    boolean isRegistered(Long id);
    boolean isActive(Long id);
}
