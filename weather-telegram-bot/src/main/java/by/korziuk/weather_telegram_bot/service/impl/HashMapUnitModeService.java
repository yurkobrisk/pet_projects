package by.korziuk.weather_telegram_bot.service.impl;

import by.korziuk.weather_telegram_bot.Unit;
import by.korziuk.weather_telegram_bot.service.UnitModeService;

import java.util.HashMap;
import java.util.Map;

public class HashMapUnitModeService implements UnitModeService {

    private static Map<Long, Unit> map = new HashMap<>();

    @Override
    public Unit getCurrentUnit(long chatId) {
        if (map.containsKey(chatId)) {
            return map.get(chatId);
        }
        return Unit.METRIC;
    }

    @Override
    public void setCurrentUnit(long chatId, Unit unit) {
        map.put(chatId, unit);
    }
}
