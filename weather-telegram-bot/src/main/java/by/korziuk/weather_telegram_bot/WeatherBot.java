package by.korziuk.weather_telegram_bot;

import by.korziuk.weather_telegram_bot.enums.Language;
import by.korziuk.weather_telegram_bot.enums.Unit;
import by.korziuk.weather_telegram_bot.model.Forecast;
import by.korziuk.weather_telegram_bot.mapper.EmojiMapper;
import by.korziuk.weather_telegram_bot.service.ForecastProcessor;
import by.korziuk.weather_telegram_bot.service.LanguageModeService;
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
    private final LanguageModeService languageModeService = LanguageModeService.getInstance();

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
        } else if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
    }

    private void handleCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        String data = callbackQuery.getData().toUpperCase();

        Unit currentUnit = Arrays.stream(Unit.values())
                .filter(element -> element.name().equals(data))
                .findFirst()
                .orElse(null);

        if (currentUnit != null) {
            unitModeService.setCurrentUnit(message.getChatId(), currentUnit);

            try {
                execute(SendMessage
                        .builder()
                        .chatId(message.getChatId().toString())
                        .text("The current unit is " + currentUnit.name().toLowerCase() + " now.")
                        .build()
                );
                return;
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        Language currentLanguage = Arrays.stream(Language.values())
                .filter(element -> element.name().equals(data))
                .findFirst()
                .orElse(null);

        if (currentLanguage != null){
            languageModeService.setCurrentLanguage(message.getChatId(), currentLanguage);

            try {
                execute(SendMessage
                        .builder()
                        .chatId(message.getChatId().toString())
                        .text("The current language is " + currentLanguage.getName() + " now.")
                        .build()
                );
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleMessage(Message message) {

        if (message.hasText()) {

            if (message.hasEntities()) {
                handleCommand(message);
                return;
            }

            String city = concatenateToString(message.getText());
            Forecast forecast = processor.getForecast(
                    "http://api.openweathermap.org/data/2.5/weather?q=" + city
                            + "&units=" + UnitModeService.getInstance().getCurrentUnit(message.getChatId())
                            + "&appid=" + APP_ID
                            + "&lang=" + LanguageModeService.getInstance().getCurrentLanguage(message.getChatId()));

            if (forecast.cod == 404) {
                try {
                    execute(SendMessage
                            .builder()
                            .chatId(message.getChatId().toString())
                            .text("Nothing to say you! "
                                    + EmojiManager.getForAlias("disappointed").getUnicode()
                                    + " City not found.")
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    execute(SendMessage
                            .builder()
                            .chatId(message.getChatId().toString())
                            .parseMode(ParseMode.HTML)
                            .text(getResponse(forecast,
                                    unitModeService.getCurrentUnit(message.getChatId()),
                                    languageModeService.getCurrentLanguage(message.getChatId())))
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
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

    private void handleCommand(Message message) {

        Optional<MessageEntity> commandEntity =
                message.getEntities().stream()
                        .filter(entity -> entity.getType().equals("bot_command"))
                        .findFirst();

        if (commandEntity.isPresent()) {
            String command = commandEntity.get().getText();

            switch (command) {
                case "/setunits" : {
                    List<List<InlineKeyboardButton>> unitButtons = new ArrayList<>();
                    Unit currentUnit = unitModeService.getCurrentUnit(message.getChatId());

                    unitButtons.add(
                            Arrays.asList(
                                    InlineKeyboardButton.builder()
                                            .text(getUnitButton(Unit.METRIC, currentUnit))
                                            .callbackData("metric")
                                            .build(),
                                    InlineKeyboardButton.builder()
                                            .text(getUnitButton(Unit.IMPERIAL, currentUnit))
                                            .callbackData("imperial")
                                            .build())
                    );

                    try {
                        execute(SendMessage
                                .builder()
                                .chatId(message.getChatId().toString())
                                .text("Choose unit")
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(unitButtons).build())
                                .build());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }

                case "/setlanguage" : {
                    List<List<InlineKeyboardButton>> languageButtons = new ArrayList<>();
                    Language currentLanguage = languageModeService.getCurrentLanguage(message.getChatId());

                    languageButtons.add(
                            Arrays.asList(
                                    InlineKeyboardButton.builder()
                                            .text(getLanguageButton(Language.EN, currentLanguage))
                                            .callbackData("en")
                                            .build(),
                                    InlineKeyboardButton.builder()
                                            .text(getLanguageButton(Language.RU, currentLanguage))
                                            .callbackData("ru")
                                            .build()
                            )
                    );

                    try {
                        execute(SendMessage
                                .builder()
                                .chatId(message.getChatId().toString())
                                .text("Choose language")
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(languageButtons).build())
                                .build());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private String concatenateToString(String stringWithSpaces) {
        if (stringWithSpaces != null) {
            return stringWithSpaces.replaceAll(" ", "+");
        } else return "";
    }

    private String getResponse(Forecast forecast, Unit unit, Language language) {
        return "<B><pre>" + forecast.name + " "
                + EmojiManager.getForAlias(forecast.sys.country.toLowerCase()).getUnicode() + "  "
                    + language.getFeelsLike() + " " + forecast.main.feels_like + getTemperature(unit) + "\r\n"
                + EmojiManager.getForAlias("chart_with_downwards_trend").getUnicode() + forecast.main.temp_min + getTemperature(unit)
                + " - " + EmojiManager.getForAlias("chart_with_upwards_trend").getUnicode() + forecast.main.temp_max + getTemperature(unit) + "\r\n"
                + emojiMapper.getEmoji(forecast.weather.get(0).icon) + "  " + forecast.weather.get(0).description + "\r\n"
                + EmojiManager.getForAlias("droplet").getUnicode() + " " + forecast.main.humidity + "% "
                    + EmojiManager.getForAlias("blowing_wind").getUnicode() + " "+ forecast.wind.speed
                    + language.getWindSpeed(unit) + " "
                    + EmojiManager.getForAlias("temperature").getUnicode() + " " + forecast.main.temp
                    + getTemperature(unit) + "\r\n"
                + EmojiManager.getForAlias("date").getUnicode()
                    + "  " + getLocalTime(forecast.timezone)
                + "</pre></B>";
    }

    private String getTemperature(Unit unit) {
        return unit == Unit.METRIC ? "" + '\u2103' : "" + '\u2109';
    }

    private String getLocalTime(int offset) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(calendar.getTimeInMillis() + (offset) * 1000L); // in milliseconds

        return String.format("%s-%s-%02d %02d:%02d %s",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.ENGLISH));
    }

    private String getUnitButton(Unit saved, Unit current) {
        return saved == current ? saved.name().toLowerCase() + "  \uD83D\uDFE2" : saved.name().toLowerCase();
    }

    private String getLanguageButton(Language saved, Language current) {
        return saved == current ? saved.name().toLowerCase() + "  \uD83D\uDFE2" : saved.name().toLowerCase();
    }
}
