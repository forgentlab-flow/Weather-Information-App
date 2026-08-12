package com.weatherapp;

import com.google.gson.JsonObject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    // =============================================================
    // APPLICATION DATA
    // =============================================================

    private final WeatherAPI weatherAPI = new WeatherAPI();

    private JsonObject currentWeather;
    private JsonObject currentForecast;

    // =============================================================
    // GUI COMPONENTS
    // =============================================================

    private TextField cityField;

    private Label cityLabel;
    private Label temperatureLabel;
    private Label conditionLabel;
    private Label weatherIcon;
    private Label feelsLikeLabel;
    private Label humidityLabel;
    private Label windLabel;
    private Label statusLabel;

    private ComboBox<String> temperatureUnitBox;
    private ComboBox<String> windUnitBox;

    private VBox forecastBox;

    private ListView<String> historyList;

    private BorderPane mainRoot;

    // =============================================================
    // START APPLICATION
    // =============================================================

    @Override
    public void start(Stage stage) {

        // ---------------------------------------------------------
        // City input
        // ---------------------------------------------------------

        cityField = new TextField();

        cityField.setPromptText(
                "Enter city name"
        );

        cityField.setPrefWidth(320);

        // ---------------------------------------------------------
        // Search button
        // ---------------------------------------------------------

        Button searchButton =
                new Button("Search Weather");

        searchButton.setPrefHeight(35);

        searchButton.setOnAction(
                event -> searchWeather()
        );

        // Allow Enter key to perform the search
        cityField.setOnAction(
                event -> searchWeather()
        );

        // ---------------------------------------------------------
        // Search area
        // ---------------------------------------------------------

        HBox searchBox =
                new HBox(
                        10,
                        cityField,
                        searchButton
                );

        searchBox.setAlignment(
                Pos.CENTER
        );

        // =========================================================
        // TEMPERATURE UNIT
        // =========================================================

        temperatureUnitBox =
                new ComboBox<>();

        temperatureUnitBox
                .getItems()
                .addAll(
                        "Celsius (°C)",
                        "Fahrenheit (°F)"
                );

        temperatureUnitBox.setValue(
                "Celsius (°C)"
        );

        temperatureUnitBox.setOnAction(
                event -> updateDisplayedUnits()
        );

        // =========================================================
        // WIND UNIT
        // =========================================================

        windUnitBox =
                new ComboBox<>();

        windUnitBox
                .getItems()
                .addAll(
                        "Meters/second (m/s)",
                        "Kilometers/hour (km/h)",
                        "Miles/hour (mph)"
                );

        windUnitBox.setValue(
                "Meters/second (m/s)"
        );

        windUnitBox.setOnAction(
                event -> updateDisplayedUnits()
        );

        // ---------------------------------------------------------
        // Unit labels
        // ---------------------------------------------------------

        Label temperatureUnitLabel =
                new Label("Temperature:");

        Label windUnitLabel =
                new Label("Wind:");

        HBox unitBox =
                new HBox(
                        10,
                        temperatureUnitLabel,
                        temperatureUnitBox,
                        new Label("     "),
                        windUnitLabel,
                        windUnitBox
                );

        unitBox.setAlignment(
                Pos.CENTER
        );

        // =========================================================
        // CURRENT WEATHER
        // =========================================================

        cityLabel =
                new Label("Search for a city");

        cityLabel.setFont(
                Font.font("Arial", 30)
        );

        // ---------------------------------------------------------
        // Local weather icon
        // ---------------------------------------------------------

        weatherIcon =
                new Label("☀");

        weatherIcon.setFont(
                Font.font("Arial", 70)
        );

        weatherIcon.setMinWidth(120);

        weatherIcon.setAlignment(
                Pos.CENTER
        );

        // ---------------------------------------------------------
        // Temperature
        // ---------------------------------------------------------

        temperatureLabel =
                new Label("--°C");

        temperatureLabel.setFont(
                Font.font("Arial", 52)
        );

        // ---------------------------------------------------------
        // Condition
        // ---------------------------------------------------------

        conditionLabel =
                new Label("Weather condition");

        conditionLabel.setFont(
                Font.font("Arial", 20)
        );

        // ---------------------------------------------------------
        // Other weather information
        // ---------------------------------------------------------

        feelsLikeLabel =
                new Label("Feels like: --°C");

        humidityLabel =
                new Label("Humidity: --%");

        windLabel =
                new Label("Wind: -- m/s");

        feelsLikeLabel.setFont(
                Font.font(16)
        );

        humidityLabel.setFont(
                Font.font(16)
        );

        windLabel.setFont(
                Font.font(16)
        );

        // ---------------------------------------------------------
        // Weather information layout
        // ---------------------------------------------------------

        VBox weatherInfo =
                new VBox(
                        8,
                        cityLabel,
                        weatherIcon,
                        temperatureLabel,
                        conditionLabel,
                        feelsLikeLabel,
                        humidityLabel,
                        windLabel
                );

        weatherInfo.setAlignment(
                Pos.CENTER
        );

        weatherInfo.setPadding(
                new Insets(20)
        );

        // ---------------------------------------------------------
        // Current weather panel
        // ---------------------------------------------------------

        TitledPane currentWeatherPane =
                new TitledPane(
                        "Current Weather",
                        weatherInfo
                );

        currentWeatherPane.setExpanded(
                true
        );

        // =========================================================
        // FORECAST
        // =========================================================

        forecastBox =
                new VBox(8);

        forecastBox.setPadding(
                new Insets(10)
        );

        TitledPane forecastPane =
                new TitledPane(
                        "Short-Term Forecast",
                        forecastBox
                );

        forecastPane.setExpanded(
                true
        );

        // =========================================================
        // HISTORY
        // =========================================================

        historyList =
                new ListView<>();

        historyList.setPrefHeight(
                160
        );

        TitledPane historyPane =
                new TitledPane(
                        "Recent Searches",
                        historyList
                );

        historyPane.setExpanded(
                true
        );

        // =========================================================
        // STATUS
        // =========================================================

        statusLabel =
                new Label("Ready");

        statusLabel.setTextFill(
                Color.DARKSLATEGRAY
        );

        // =========================================================
        // MAIN CONTENT
        // =========================================================

        VBox content =
                new VBox(
                        15,
                        searchBox,
                        unitBox,
                        currentWeatherPane,
                        forecastPane,
                        historyPane,
                        statusLabel
                );

        content.setPadding(
                new Insets(20)
        );

        content.setAlignment(
                Pos.TOP_CENTER
        );

        // =========================================================
        // MAIN ROOT
        // =========================================================

        mainRoot =
                new BorderPane();

        mainRoot.setCenter(
                content
        );

        setDefaultBackground();

        // =========================================================
        // SCENE
        // =========================================================

        Scene scene =
                new Scene(
                        mainRoot,
                        900,
                        800
                );

        stage.setTitle(
                "Weather Information App"
        );

        stage.setScene(
                scene
        );

        stage.setMinWidth(750);
        stage.setMinHeight(700);

        stage.show();
    }

    // =============================================================
    // SEARCH WEATHER
    // =============================================================

    private void searchWeather() {

        String city =
                cityField.getText().trim();

        // ---------------------------------------------------------
        // Validate city input
        // ---------------------------------------------------------

        if (city.isEmpty()) {

            showError(
                    "Please enter a city name."
            );

            return;
        }

        statusLabel.setText(
                "Loading weather information..."
        );

        try {

            // -----------------------------------------------------
            // Get current weather
            // -----------------------------------------------------

            currentWeather =
                    weatherAPI
                            .getCurrentWeather(city);

            displayCurrentWeather(
                    currentWeather
            );

            // -----------------------------------------------------
            // Get forecast
            // -----------------------------------------------------

            currentForecast =
                    weatherAPI
                            .getForecast(city);

            displayForecast(
                    currentForecast
            );

            // -----------------------------------------------------
            // Add search to history
            // -----------------------------------------------------

            addToHistory(city);

            statusLabel.setText(
                    "Weather information updated successfully."
            );

        } catch (IllegalArgumentException e) {

            e.printStackTrace();

            showError(
                    e.getMessage()
            );

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    "Unable to retrieve weather information.\n\n"
                            + e.getMessage()
            );
        }
    }

    // =============================================================
    // DISPLAY CURRENT WEATHER
    // =============================================================

    private void displayCurrentWeather(
            JsonObject weather) {

        // ---------------------------------------------------------
        // City
        // ---------------------------------------------------------

        String city =
                weather
                        .get("name")
                        .getAsString();

        // ---------------------------------------------------------
        // Weather condition
        // ---------------------------------------------------------

        JsonObject weatherData =
                weather
                        .getAsJsonArray("weather")
                        .get(0)
                        .getAsJsonObject();

        String condition =
                weatherData
                        .get("description")
                        .getAsString();

        // ---------------------------------------------------------
        // Weather icon code
        // ---------------------------------------------------------

        String iconCode =
                weatherData
                        .get("icon")
                        .getAsString();

        // ---------------------------------------------------------
        // Display basic information
        // ---------------------------------------------------------

        cityLabel.setText(city);

        conditionLabel.setText(
                capitalize(condition)
        );

        // ---------------------------------------------------------
        // Display local weather icon
        // ---------------------------------------------------------

        weatherIcon.setText(
                getWeatherIcon(iconCode)
        );

        // ---------------------------------------------------------
        // Update temperature and wind
        // ---------------------------------------------------------

        updateDisplayedUnits();

        // ---------------------------------------------------------
        // Update background
        // ---------------------------------------------------------

        updateBackground(weather);
    }

    // =============================================================
    // WEATHER ICON
    // =============================================================

    private String getWeatherIcon(
            String iconCode) {

        switch (iconCode) {

            // -----------------------------------------------------
            // Clear sky
            // -----------------------------------------------------

            case "01d":
                return "☀";

            case "01n":
                return "☾";

            // -----------------------------------------------------
            // Few clouds
            // -----------------------------------------------------

            case "02d":
                return "🌤";

            case "02n":
                return "☁";

            // -----------------------------------------------------
            // Scattered clouds
            // -----------------------------------------------------

            case "03d":
            case "03n":
                return "☁";

            // -----------------------------------------------------
            // Broken clouds
            // -----------------------------------------------------

            case "04d":
            case "04n":
                return "☁";

            // -----------------------------------------------------
            // Shower rain
            // -----------------------------------------------------

            case "09d":
            case "09n":
                return "🌧";

            // -----------------------------------------------------
            // Rain
            // -----------------------------------------------------

            case "10d":
                return "🌦";

            case "10n":
                return "🌧";

            // -----------------------------------------------------
            // Thunderstorm
            // -----------------------------------------------------

            case "11d":
            case "11n":
                return "⛈";

            // -----------------------------------------------------
            // Snow
            // -----------------------------------------------------

            case "13d":
            case "13n":
                return "❄";

            // -----------------------------------------------------
            // Mist
            // -----------------------------------------------------

            case "50d":
            case "50n":
                return "🌫";

            // -----------------------------------------------------
            // Unknown weather condition
            // -----------------------------------------------------

            default:
                return "☁";
        }
    }

    // =============================================================
    // UNIT CONVERSION
    // =============================================================

    private void updateDisplayedUnits() {

        if (currentWeather == null) {
            return;
        }

        // ---------------------------------------------------------
        // Get weather data
        // ---------------------------------------------------------

        JsonObject main =
                currentWeather
                        .getAsJsonObject("main");

        JsonObject wind =
                currentWeather
                        .getAsJsonObject("wind");

        double temperature =
                main
                        .get("temp")
                        .getAsDouble();

        double feelsLike =
                main
                        .get("feels_like")
                        .getAsDouble();

        double windSpeed =
                wind
                        .get("speed")
                        .getAsDouble();

        int humidity =
                main
                        .get("humidity")
                        .getAsInt();

        // =========================================================
        // TEMPERATURE CONVERSION
        // =========================================================

        String temperatureUnit =
                temperatureUnitBox.getValue();

        double convertedTemperature =
                temperature;

        double convertedFeelsLike =
                feelsLike;

        String temperatureSymbol =
                "°C";

        if (
                temperatureUnit.equals(
                        "Fahrenheit (°F)"
                )
        ) {

            convertedTemperature =
                    (temperature * 9 / 5) + 32;

            convertedFeelsLike =
                    (feelsLike * 9 / 5) + 32;

            temperatureSymbol =
                    "°F";
        }

        temperatureLabel.setText(
                String.format(
                        "%.1f%s",
                        convertedTemperature,
                        temperatureSymbol
                )
        );

        feelsLikeLabel.setText(
                String.format(
                        "Feels like: %.1f%s",
                        convertedFeelsLike,
                        temperatureSymbol
                )
        );

        // =========================================================
        // HUMIDITY
        // =========================================================

        humidityLabel.setText(
                "Humidity: "
                        + humidity
                        + "%"
        );

        // =========================================================
        // WIND CONVERSION
        // =========================================================

        String windUnit =
                windUnitBox.getValue();

        double convertedWind =
                windSpeed;

        String windSymbol =
                "m/s";

        if (
                windUnit.equals(
                        "Kilometers/hour (km/h)"
                )
        ) {

            convertedWind =
                    windSpeed * 3.6;

            windSymbol =
                    "km/h";

        } else if (
                windUnit.equals(
                        "Miles/hour (mph)"
                )
        ) {

            convertedWind =
                    windSpeed * 2.236936;

            windSymbol =
                    "mph";
        }

        windLabel.setText(
                String.format(
                        "Wind: %.1f %s",
                        convertedWind,
                        windSymbol
                )
        );

        // ---------------------------------------------------------
        // Refresh forecast
        // ---------------------------------------------------------

        if (currentForecast != null) {

            displayForecast(
                    currentForecast
            );
        }
    }

    // =============================================================
    // DISPLAY FORECAST
    // =============================================================

    private void displayForecast(
            JsonObject forecast) {

        forecastBox
                .getChildren()
                .clear();

        var forecastList =
                forecast
                        .getAsJsonArray("list");

        int itemsToDisplay =
                Math.min(
                        5,
                        forecastList.size()
                );

        String temperatureUnit =
                temperatureUnitBox.getValue();

        for (
                int i = 0;
                i < itemsToDisplay;
                i++
        ) {

            JsonObject item =
                    forecastList
                            .get(i)
                            .getAsJsonObject();

            // -----------------------------------------------------
            // Date and time
            // -----------------------------------------------------

            String dateTime =
                    item
                            .get("dt_txt")
                            .getAsString();

            // -----------------------------------------------------
            // Temperature
            // -----------------------------------------------------

            double temperature =
                    item
                            .getAsJsonObject("main")
                            .get("temp")
                            .getAsDouble();

            // -----------------------------------------------------
            // Humidity
            // -----------------------------------------------------

            int humidity =
                    item
                            .getAsJsonObject("main")
                            .get("humidity")
                            .getAsInt();

            // -----------------------------------------------------
            // Weather condition
            // -----------------------------------------------------

            String condition =
                    item
                            .getAsJsonArray("weather")
                            .get(0)
                            .getAsJsonObject()
                            .get("description")
                            .getAsString();

            // -----------------------------------------------------
            // Weather icon
            // -----------------------------------------------------

            String iconCode =
                    item
                            .getAsJsonArray("weather")
                            .get(0)
                            .getAsJsonObject()
                            .get("icon")
                            .getAsString();

            String icon =
                    getWeatherIcon(iconCode);

            // -----------------------------------------------------
            // Temperature conversion
            // -----------------------------------------------------

            String temperatureSymbol =
                    "°C";

            if (
                    temperatureUnit.equals(
                            "Fahrenheit (°F)"
                    )
            ) {

                temperature =
                        (temperature * 9 / 5) + 32;

                temperatureSymbol =
                        "°F";
            }

            // -----------------------------------------------------
            // Forecast text
            // -----------------------------------------------------

            String forecastText =
                    icon
                            + "  "
                            + dateTime
                            + "  |  "
                            + String.format(
                            "%.1f%s",
                            temperature,
                            temperatureSymbol
                    )
                            + "  |  Humidity "
                            + humidity
                            + "%  |  "
                            + capitalize(condition);

            Label forecastLabel =
                    new Label(
                            forecastText
                    );

            forecastLabel.setFont(
                    Font.font(14)
            );

            forecastBox
                    .getChildren()
                    .add(
                            forecastLabel
                    );
        }
    }

    // =============================================================
    // SEARCH HISTORY
    // =============================================================

    private void addToHistory(
            String city) {

        String timestamp =
                LocalDateTime
                        .now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd HH:mm"
                                )
                        );

        historyList
                .getItems()
                .add(
                        0,
                        city
                                + " — "
                                + timestamp
                );
    }

    // =============================================================
    // DYNAMIC BACKGROUND
    // =============================================================

    private void updateBackground(
            JsonObject weather) {

        long currentTime =
                weather
                        .get("dt")
                        .getAsLong();

        long sunrise =
                weather
                        .getAsJsonObject("sys")
                        .get("sunrise")
                        .getAsLong();

        long sunset =
                weather
                        .getAsJsonObject("sys")
                        .get("sunset")
                        .getAsLong();

        String background;

        // ---------------------------------------------------------
        // Before sunrise
        // ---------------------------------------------------------

        if (currentTime < sunrise) {

            background =
                    "linear-gradient("
                            + "to bottom, "
                            + "#0f2027, "
                            + "#203a43, "
                            + "#2c5364"
                            + ")";

        // ---------------------------------------------------------
        // Daytime
        // ---------------------------------------------------------

        } else if (currentTime < sunset) {

            background =
                    "linear-gradient("
                            + "to bottom, "
                            + "#74ebd5, "
                            + "#ACB6E5"
                            + ")";

        // ---------------------------------------------------------
        // Evening / night
        // ---------------------------------------------------------

        } else {

            background =
                    "linear-gradient("
                            + "to bottom, "
                            + "#2c3e50, "
                            + "#4ca1af"
                            + ")";
        }

        mainRoot.setStyle(
                "-fx-background-color: "
                        + background
                        + ";"
        );
    }

    // =============================================================
    // DEFAULT BACKGROUND
    // =============================================================

    private void setDefaultBackground() {

        mainRoot.setStyle(
                "-fx-background-color: "
                        + "linear-gradient("
                        + "to bottom, "
                        + "#eaf4ff, "
                        + "#ffffff"
                        + ");"
        );
    }

    // =============================================================
    // CAPITALIZE TEXT
    // =============================================================

    private String capitalize(
            String text) {

        if (
                text == null
                        || text.isEmpty()
        ) {

            return text;
        }

        return Character
                .toUpperCase(
                        text.charAt(0)
                )
                + text.substring(1);
    }

    // =============================================================
    // ERROR HANDLING
    // =============================================================

    private void showError(
            String message) {

        statusLabel.setText(
                "Error: " + message
        );

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setTitle(
                "Weather Information App"
        );

        alert.setHeaderText(
                "Unable to retrieve weather"
        );

        alert.setContentText(
                message
        );

        alert.showAndWait();
    }

    // =============================================================
    // MAIN METHOD
    // =============================================================

    public static void main(
            String[] args) {

        launch(args);
    }
}