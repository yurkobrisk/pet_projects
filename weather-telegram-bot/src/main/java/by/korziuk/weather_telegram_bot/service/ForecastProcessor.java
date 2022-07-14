package by.korziuk.weather_telegram_bot.service;

import by.korziuk.weather_telegram_bot.model.Forecast;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class ForecastProcessor {

    public Forecast getForecast(String url) {

        HttpLoader loader = new HttpLoader();
        String json = loader.get(url);

//        System.out.println(json);

        Gson gson = new Gson();

        return gson.fromJson(json, Forecast.class);
    }
}
