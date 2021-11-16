package weather.app.service;

import com.google.gson.Gson;
import weather.app.model.Forecast;

public class ForecastProcessor {

    public Forecast getForecast(String url) {

        HttpLoader loader = new HttpLoader();
        String json = loader.get(url);

        System.out.println(json);

        Gson gson = new Gson();

        return gson.fromJson(json, Forecast.class);
    }
}
