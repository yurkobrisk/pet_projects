package by.korziuk.weather_telegram_bot.service;

import by.korziuk.weather_telegram_bot.enums.Language;
import by.korziuk.weather_telegram_bot.service.impl.HashMapLanguageModeService;

public interface LanguageModeService{

    static LanguageModeService getInstance(){
        return new HashMapLanguageModeService();
    }

    Language getCurrentLanguage(long chatId);

    void setCurrentLanguage(long chatId, Language language);
}
