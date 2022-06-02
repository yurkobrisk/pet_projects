package by.korziuk.weather_telegram_bot.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpLoader {

    public String get(String url) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        HttpClient httpClient = HttpClient.newBuilder()
                .executor(executor)
                .build();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdownNow();
            httpClient = null;
            System.gc();
        }
        return "";
    }
}
