package by.korziuk.weather_telegram_bot.service.impl;

import by.korziuk.weather_telegram_bot.enums.Unit;
import by.korziuk.weather_telegram_bot.service.UnitModeService;

import java.util.HashMap;
import java.util.Map;

public class HashMapUnitModeService implements UnitModeService {

    private static Map<Long, Unit> unitMap = new HashMap<>();

    @Override
    public Unit getCurrentUnit(long chatId) {
        if (unitMap.containsKey(chatId)) {
            return unitMap.get(chatId);
        }
        return Unit.METRIC;
    }

    @Override
    public void setCurrentUnit(long chatId, Unit unit) {
        unitMap.put(chatId, unit);
    }
}
