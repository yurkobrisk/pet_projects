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

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

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
                int offset = Integer.parseInt(forecast.timezone);
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                calendar.setTimeInMillis(calendar.getTimeInMillis() + offset * 1000L); // in milliseconds

                String localTime = String.format("%s-%s-%02d %02d:%02d %s",
                        calendar.get(Calendar.YEAR),
                        calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.ENGLISH));
                try {
                    execute(SendMessage
                            .builder()
                            .chatId(message.getChatId().toString())
                            .parseMode(ParseMode.HTML)
                            .text("<B><pre>" + forecast.name + ", "
                                    + EmojiManager.getForAlias(forecast.sys.country.toLowerCase()).getUnicode() + ": "
                                    + forecast.main.temp + '\u2103' + "\r\n"
                                    + "Max: " + forecast.main.temp_max + '\u2103' + " - Min: " + forecast.main.temp_min + '\u2103' + "\r\n"
                                    + forecast.weather.get(0).main + " " + emojiMapper.getEmoji(forecast.weather.get(0).icon) + " "
                                    + forecast.weather.get(0).description + "\r\n"
                                    + EmojiManager.getForAlias("droplet").getUnicode() + " " + forecast.main.humidity + "% "
                                    + EmojiManager.getForAlias("blowing_wind").getUnicode() + " "+ forecast.wind.speed + "m/s "
                                    + EmojiManager.getForAlias("temperature").getUnicode() + " " + forecast.main.feels_like + '\u2103' + "\r\n"
                                    + EmojiManager.getForAlias("calendar").getUnicode() + "Local Time: " + localTime + "</pre></B>")
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
