package by.korziuk.weather_telegram_bot.service;

import by.korziuk.weather_telegram_bot.enums.Unit;
import by.korziuk.weather_telegram_bot.service.impl.HashMapUnitModeService;

public interface UnitModeService {

    static UnitModeService getInstance(){
        return new HashMapUnitModeService();
    }

    Unit getCurrentUnit(long chatId);

    void setCurrentUnit(long chatId, Unit unit);
}
