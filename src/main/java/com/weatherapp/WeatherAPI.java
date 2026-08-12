package com.weatherapp;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WeatherAPI {

    private static final String API_KEY =
            System.getenv("OPENWEATHER_API_KEY");

    private static final String CURRENT_WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather";

    private static final String FORECAST_URL =
            "https://api.openweathermap.org/data/2.5/forecast";

    private final HttpClient httpClient;

    public WeatherAPI() {
        httpClient = HttpClient.newHttpClient();
    }

    public JsonObject getCurrentWeather(String city)
            throws IOException, InterruptedException {

        validateApiKey();

        String encodedCity =
                URLEncoder.encode(
                        city,
                        StandardCharsets.UTF_8
                );

        String url =
                CURRENT_WEATHER_URL
                        + "?q=" + encodedCity
                        + "&appid=" + API_KEY
                        + "&units=metric";

        return sendRequest(url);
    }

    public JsonObject getForecast(String city)
            throws IOException, InterruptedException {

        validateApiKey();

        String encodedCity =
                URLEncoder.encode(
                        city,
                        StandardCharsets.UTF_8
                );

        String url =
                FORECAST_URL
                        + "?q=" + encodedCity
                        + "&appid=" + API_KEY
                        + "&units=metric";

        return sendRequest(url);
    }

    private JsonObject sendRequest(String url)
            throws IOException, InterruptedException {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() == 200) {

            return JsonParser
                    .parseString(response.body())
                    .getAsJsonObject();
        }

        if (response.statusCode() == 401) {

            throw new IOException(
                    "Invalid or inactive OpenWeather API key."
            );
        }

        if (response.statusCode() == 404) {

            throw new IllegalArgumentException(
                    "City not found. Please check the city name."
            );
        }

        if (response.statusCode() == 429) {

            throw new IOException(
                    "Too many API requests. Please try again later."
            );
        }

        throw new IOException(
                "Weather service error. HTTP status: "
                        + response.statusCode()
        );
    }

    private void validateApiKey() {

        if (API_KEY == null || API_KEY.isBlank()) {

            throw new IllegalStateException(
                    "OPENWEATHER_API_KEY is not configured."
            );
        }
    }
}