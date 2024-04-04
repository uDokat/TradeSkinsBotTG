package ua.dokat.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackQueryProcessingService {
    void processingCallbackQuery(Update update);
}
