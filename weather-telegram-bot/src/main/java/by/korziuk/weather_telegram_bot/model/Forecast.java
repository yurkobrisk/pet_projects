package by.korziuk.weather_telegram_bot.model;

import java.util.List;

public class Forecast {

    public Main main;
    public List<Weather> weather;
    public String name;
    public Wind wind;
    public Sys sys;
    public Clouds clouds;
    public Coord coord;
    public String cod;
    public String dt;
    public String timezone;
}