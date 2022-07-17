package by.korziuk.weather_telegram_bot.model;

import org.junit.jupiter.api.*;
import java.util.ArrayList;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class ForecastTest {
    static Forecast forecast;

    @BeforeAll
    static void setup() {

        forecast = new Forecast();
        forecast.main = new Main();
        forecast.main.temp_max = 15.86;
        forecast.main.temp_min = 15.41;
        forecast.main.temp = 15.86;
        forecast.main.feels_like = 15.8;
        forecast.main.pressure = 1014;
        forecast.main.humidity = 88;
        forecast.main.sea_level = 1014;
        forecast.main.grnd_level = 988;

        forecast.weather = new ArrayList<>();
        forecast.weather.add(new Weather());
        forecast.weather.get(0).id = 803;
        forecast.weather.get(0).main = "Clouds";
        forecast.weather.get(0).description = "broken clouds";
        forecast.weather.get(0).icon = "04d";

        forecast.name = "Minsk";

        forecast.wind = new Wind();
        forecast.wind.deg = 226;
        forecast.wind.speed =  3.21;
        forecast.wind.gust = 7.15;

        forecast.sys = new Sys();
        forecast.sys.country = "BY";
        forecast.sys.id = 8939;
        forecast.sys.type = 1;
        forecast.sys.sunrise = 1657936704;
        forecast.sys.sunset = 1657996364;

        forecast.clouds = new Clouds();
        forecast.clouds.all = 60;

        forecast.coord = new Coord();
        forecast.coord.lat = 53.9;
        forecast.coord.lon = 27.5667;

        forecast.cod = 200;
        forecast.timezone = 10800;
        forecast.dt = 1657993701;

    }

    @AfterAll
    static void clenup() {
        forecast = null;
    }

    @Test
    @DisplayName("Forecast should be equels json")
    void forecast_should_be_equels_json(){
        assertThatJson(forecast).isEqualTo("{\"coord\":{\"lon\":27.5667,\"lat\":53.9}," +
                "\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}]," +
                "\"main\":{\"temp\":15.86,\"feels_like\":15.8,\"temp_min\":15.41,\"temp_max\":15.86,\"pressure\":1014,\"humidity\":88,\"sea_level\":1014,\"grnd_level\":988}," +
                "\"wind\":{\"speed\":3.21,\"deg\":226,\"gust\":7.15}," +
                "\"clouds\":{\"all\":60},\"dt\":1657993701," +
                "\"sys\":{\"type\":1,\"id\":8939,\"country\":\"BY\",\"sunrise\":1657936704,\"sunset\":1657996364}," +
                "\"timezone\":10800," +
                "\"name\":\"Minsk\"," +
                "\"cod\":200}");
    }

    @Test
    @DisplayName("Forecast name should be Minsk")
    void forecast_name_should_be_minsk(){
        assertThat(forecast.name).startsWith("Min");
    }
}