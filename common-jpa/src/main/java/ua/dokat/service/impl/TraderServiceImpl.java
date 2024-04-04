package ua.dokat.service.impl;

import org.springframework.stereotype.Service;
import ua.dokat.dao.TraderDAO;
import ua.dokat.entity.TraderEntity;
import ua.dokat.service.TraderService;

import java.util.ArrayList;

@Service
public class TraderServiceImpl implements TraderService {

    private final TraderDAO traderDAO;

    public TraderServiceImpl(TraderDAO traderDAO) {
        this.traderDAO = traderDAO;
    }

    @Override
    public TraderEntity createAndSaveTrader(Long id) {
        TraderEntity trader = TraderEntity.builder()
                .telegramUserId(id)
                .items(new ArrayList<>())
                .build();

        return traderDAO.save(trader);
    }

    @Override
    public TraderEntity findTrader(Long id) {
        return traderDAO.findByTelegramUserId(id);
    }

    @Override
    public TraderEntity saveTrader(TraderEntity entity) {
        return traderDAO.save(entity);
    }

    @Override
    public boolean isRegistered(Long id) {
        return findTrader(id) != null;
    }

    @Override
    public boolean isActive(Long id) {
        return findTrader(id).isActive();
    }
}
