package by.korziuk.weather_telegram_bot;

import by.korziuk.weather_telegram_bot.model.Forecast;
import by.korziuk.weather_telegram_bot.service.EmojiMapper;
import by.korziuk.weather_telegram_bot.service.ForecastProcessor;
import com.vdurmont.emoji.EmojiManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value(value = "${application.token}")
    private final String TOKEN = null;
    @Value(value = "${application.username}")
    private final String USERNAME = null;
    @Value(value = "${application.id}")
    private final String APP_ID = null;

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
                                .text("Nothing to say you! "
                                        + EmojiManager.getForAlias("disappointed").getUnicode()
                                        + " City not found.")
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
                            .text(getResponce(forecast))
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    execute(SendMessage
                            .builder()
                            .chatId(message.getChatId().toString())
                            .text("Nothing to say you! "
                                    + EmojiManager.getForAlias("disappointed").getUnicode()
                                    + " I think it isn`t the city name. Try again, please.")
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

    private String getLocalTime(String offset) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(calendar.getTimeInMillis() + (Integer.parseInt(offset)) * 1000L); // in milliseconds

        return String.format("%s-%s-%02d %02d:%02d %s",
                calendar.get(Calendar.YEAR),
                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.ENGLISH));
    }

    private String getResponce(Forecast forecast) {
        return "<B><pre>" + forecast.name + ", "
                + EmojiManager.getForAlias(forecast.sys.country.toLowerCase()).getUnicode()
                    + ": " + forecast.main.temp + '\u2103' + "\r\n"
                + "Max: " + forecast.main.temp_max + '\u2103' + " - Min: " + forecast.main.temp_min + '\u2103' + "\r\n"
                + forecast.weather.get(0).main + " " + emojiMapper.getEmoji(forecast.weather.get(0).icon) + " "
                    + forecast.weather.get(0).description + "\r\n"
                + EmojiManager.getForAlias("droplet").getUnicode() + " " + forecast.main.humidity + "% "
                    + EmojiManager.getForAlias("blowing_wind").getUnicode() + " "+ forecast.wind.speed + "m/s "
                    + EmojiManager.getForAlias("temperature").getUnicode() + " " + forecast.main.feels_like
                    + '\u2103' + "\r\n"
                + "Local Time: " + EmojiManager.getForAlias("calendar").getUnicode()
                    + " " + getLocalTime(forecast.timezone)
                + "</pre></B>";
    }
}
