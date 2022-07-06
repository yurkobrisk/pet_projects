package by.korziuk.weather_telegram_bot.service.impl;

import by.korziuk.weather_telegram_bot.Language;
import by.korziuk.weather_telegram_bot.Unit;
import by.korziuk.weather_telegram_bot.service.LanguageModeService;

import java.util.HashMap;
import java.util.Map;

public class HashMapLanguageModeService implements LanguageModeService {

    private static Map<Long, Language> languageMap = new HashMap<>();

    @Override
    public Language getCurrentLanguage(long chatId) {
        if (languageMap.containsKey(chatId)) {
            return languageMap.get(chatId);
        }
        return Language.EN;
    }

    @Override
    public void setCurrentLanguage(long chatId, Language language) {
        languageMap.put(chatId, language);
    }
}
