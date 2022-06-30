package by.korziuk.weather_telegram_bot;

import by.korziuk.weather_telegram_bot.model.Forecast;
import by.korziuk.weather_telegram_bot.mapper.EmojiMapper;
import by.korziuk.weather_telegram_bot.service.ForecastProcessor;
import by.korziuk.weather_telegram_bot.service.UnitModeService;
import com.vdurmont.emoji.EmojiManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
public class WeatherBot extends TelegramLongPollingBot {

    @Value(value = "${application.token}")
    private final String TOKEN = null;
    @Value(value = "${application.username}")
    private final String USERNAME = null;
    @Value(value = "${application.id}")
    private final String APP_ID = null;

    private final UnitModeService unitModeService = UnitModeService.getInstance();

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
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        }


        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                if (message.hasEntities()) {
                    Optional<MessageEntity> commandEntity =
                            message.getEntities().stream()
                                    .filter(entity -> "bot_command".equals(entity.getType()))
                                    .findFirst();

                    if (commandEntity.isPresent()) {
                        List<List<InlineKeyboardButton>> units = new ArrayList<>();

                        Unit enumUnit = unitModeService.getCurrentUnit(message.getChatId());

                        units.add(
                                Arrays.asList(
                                    InlineKeyboardButton.builder()
                                            .text(getUnitButton(Unit.METRIC, enumUnit))
                                            .callbackData("metric")
                                            .build(),
                                    InlineKeyboardButton.builder()
                                            .text(getUnitButton(Unit.IMPERIAL, enumUnit))
                                            .callbackData("imperial")
                                            .build())
                        );

                        String command = message
                                .getText()
                                .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());

                        switch (command) {
                            case "/setunits" :
                                try {
                                    execute(SendMessage
                                            .builder()
                                            .chatId(message.getChatId().toString())
                                            .text("Choose units")
                                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(units).build())
                                            .build());

                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case "/setlanguage" :
                                try {
                                    execute(SendMessage
                                            .builder()
                                            .chatId(message.getChatId().toString())
                                            .text("Choose language")
                                            .build());
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                        }
                    }
                    return;
                }

                String city = concatenateToString(message.getText());
                Forecast forecast = processor.getForecast(
                        "http://api.openweathermap.org/data/2.5/weather?q=" + city
                                + "&units=" + UnitModeService.getInstance().getCurrentUnit(message.getChatId())
                                + "&appid=" + APP_ID);

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
                            .text(getResponse(forecast, unitModeService.getCurrentUnit(message.getChatId())))
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

    private void handleCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
//        unit = callbackQuery.getData();
        Unit unitEnum = Unit.valueOf(callbackQuery.getData().toUpperCase());
        unitModeService.setCurrentUnit(message.getChatId(), unitEnum);

        try {
            execute(SendMessage
                        .builder()
                        .chatId(message.getChatId().toString())
                        .text("The unit is " + unitEnum.name().toLowerCase() + " now.")
                        .build()
            );
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
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

    private String getResponse(Forecast forecast, Unit unit) {
        return "<B><pre>" + forecast.name + ", "
                + EmojiManager.getForAlias(forecast.sys.country.toLowerCase()).getUnicode()
                    + ": feels like " + forecast.main.feels_like + getTemperature(unit) + "\r\n"
                + "Min " + forecast.main.temp_min + getTemperature(unit)
                + " - Max " + forecast.main.temp_max + getTemperature(unit) + "\r\n"
                + forecast.weather.get(0).main + " " + emojiMapper.getEmoji(forecast.weather.get(0).icon) + "  "
                    + forecast.weather.get(0).description + "\r\n"
                + EmojiManager.getForAlias("droplet").getUnicode() + " " + forecast.main.humidity + "% "
                    + EmojiManager.getForAlias("blowing_wind").getUnicode() + " "+ forecast.wind.speed
                    + getWindSpeed(unit)
                    + EmojiManager.getForAlias("temperature").getUnicode() + " " + forecast.main.temp
                    + getTemperature(unit) + "\r\n"
                + "Local Time " + EmojiManager.getForAlias("date").getUnicode()
                    + "  " + getLocalTime(forecast.timezone)
                + "</pre></B>";
    }

    private String getUnitButton(Unit saved, Unit current) {
        return saved == current ? saved.name().toLowerCase() + "  \uD83D\uDFE2" : saved.name().toLowerCase();
    }

    private String getTemperature(Unit unit) {
        return unit == Unit.METRIC ? " " + '\u2103' : " " + '\u2109';
    }

    private String getWindSpeed(Unit unit) {
        return unit == Unit.METRIC ? " meter/sec " : " miles/hour ";
    }
}
