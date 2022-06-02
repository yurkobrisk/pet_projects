package by.korziuk.weather_telegram_bot;

import by.korziuk.weather_telegram_bot.model.Forecast;
import by.korziuk.weather_telegram_bot.service.EmojiMapper;
import by.korziuk.weather_telegram_bot.service.ForecastProcessor;
import com.vdurmont.emoji.EmojiManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class WeatherBot extends TelegramLongPollingBot {

    private static final String TOKEN = "5571834933:AAFJbWAZyXZbdQRiMNwfd77RZ29qIlzf6OA";
    private static final String USERNAME = "@ykweatherBot";
    private static final String APP_ID = "1d3e0f1f78a754b9d3f7a810ebf65783";

    @Autowired
    EmojiMapper emojiMapper;
    @Autowired
    ForecastProcessor processor;

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                String city = concatenateToString(message.getText());
                Forecast forecast = processor.getForecast(
                        "http://api.openweathermap.org/data/2.5/weather?q="
                                + city + "&units=metric&appid=" + APP_ID);

                if (forecast.cod.equals("404")) {
                    try {
                        execute(SendMessage
                                .builder()
                                .chatId(message.getChatId().toString())
                                .text("Nothing to say you! :) City not found.")
                                .build());
                        return;
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    execute(SendMessage
                            .builder()
                            .chatId(message.getChatId().toString())
                            .parseMode(ParseMode.HTML)
                            .text("<B><pre>" + forecast.name + ", " + forecast.sys.country + ": " + forecast.main.temp + '\u2103' + "\r\n"
                                    + "Max: " + forecast.main.temp_max + '\u2103' + " - Min: " + forecast.main.temp_min + '\u2103' + "\r\n"
                                    + forecast.weather.get(0).main + " " + emojiMapper.getEmoji(forecast.weather.get(0).description) + " "
                                    + forecast.weather.get(0).description + "\r\n"
                                    + EmojiManager.getForAlias("droplet").getUnicode() + " " + forecast.main.humidity + "% "
                                    + EmojiManager.getForAlias("blowing_wind").getUnicode() + forecast.wind.speed + "m/s "
                                    + EmojiManager.getForAlias("temperature").getUnicode() + forecast.main.feels_like + '\u2103' + "\r\n"
                                    + "local time: " + "</pre></B>")
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String concatenateToString(String stringWithSpaces) {
        if (stringWithSpaces != null) {
            return stringWithSpaces.replaceAll(" ", "+");
        } else return "";
    }
}
