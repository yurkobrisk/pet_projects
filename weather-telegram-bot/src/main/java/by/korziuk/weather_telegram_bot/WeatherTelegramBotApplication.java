package by.korziuk.weather_telegram_bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@SpringBootApplication
//@ConfigurationPropertiesScan("by.korziuk.weather_telegram_bot.config")
public class WeatherTelegramBotApplication {

	@Autowired
	WeatherBot weatherBot;

	public static void main(String[] args) {
		SpringApplication.run(WeatherTelegramBotApplication.class, args);
	}

	@PostConstruct
	public void init() {
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(weatherBot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
