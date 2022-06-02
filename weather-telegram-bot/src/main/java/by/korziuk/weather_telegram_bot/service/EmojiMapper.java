package by.korziuk.weather_telegram_bot.service;

import com.vdurmont.emoji.EmojiManager;
import org.springframework.stereotype.Component;

@Component
public class EmojiMapper {

    public String getEmoji(String description) {
        switch (description) {
            case ("clear sky") : return EmojiManager.getForAlias("sunny").getUnicode();
            case ("few clouds") : return EmojiManager.getForAlias("white_sun_small_cloud").getUnicode();
            case ("scattered clouds") : return EmojiManager.getForAlias("partly_sunny").getUnicode();
            case ("broken clouds") : return EmojiManager.getForAlias("cloud").getUnicode();
            case ("overcast clouds") : return EmojiManager.getForAlias("cloud").getUnicode() + EmojiManager.getForAlias("cloud").getUnicode();
            case ("shower rain") : return EmojiManager.getForAlias("cloud_rain").getUnicode();
            case ("rain") : return EmojiManager.getForAlias("white_sun_behind_cloud_rain").getUnicode();
            case ("thunderstorm") : return EmojiManager.getForAlias("cloud_lightning").getUnicode();
            case ("snow") : return EmojiManager.getForAlias("snowflake").getUnicode();
            case ("mist") : return EmojiManager.getForAlias("fog").getUnicode();
        }
        return null;
    }
}
