package by.korziuk.weather_telegram_bot.mapper;

import com.vdurmont.emoji.EmojiManager;
import org.springframework.stereotype.Component;

@Component
public class EmojiMapper {

    public String getEmoji(String icon) {
        switch (icon) {
            case ("01d") : return EmojiManager.getForAlias("sunny").getUnicode();
            case ("01n") : return EmojiManager.getForAlias("crescent_moon").getUnicode();
            case ("02d") : return EmojiManager.getForAlias("white_sun_small_cloud").getUnicode();
            case ("02n") : return EmojiManager.getForAlias("crescent_moon").getUnicode() + EmojiManager.getForAlias("cloud").getUnicode();
            case ("03d") : return EmojiManager.getForAlias("partly_sunny").getUnicode();
            case ("03n") : return EmojiManager.getForAlias("crescent_moon").getUnicode() + EmojiManager.getForAlias("cloud").getUnicode();
            case ("04d") :
            case ("04n") : return EmojiManager.getForAlias("cloud").getUnicode() + EmojiManager.getForAlias("cloud").getUnicode();
            case ("09d") :
            case ("09n") :
            case ("10n") : return EmojiManager.getForAlias("cloud_rain").getUnicode();
            case ("10d") : return EmojiManager.getForAlias("white_sun_behind_cloud_rain").getUnicode();
            case ("11d") :
            case ("11n") : return EmojiManager.getForAlias("cloud_lightning").getUnicode();
            case ("13d") :
            case ("13n") : return EmojiManager.getForAlias("snowflake").getUnicode();
            case ("50d") :
            case ("50n") : return EmojiManager.getForAlias("fog").getUnicode();
        }
        return null;
    }
}
