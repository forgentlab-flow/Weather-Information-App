# Weather Information App

## Overview

The Weather Information App is a Java desktop application developed using JavaFX. It connects to the OpenWeatherMap API to retrieve and display current weather information and short-term weather forecasts for a selected city.

The application provides a graphical user interface that allows users to search for weather information, change measurement units, view forecasts, and review recent searches.

## Features

- Search weather information by city name
- Retrieve real-time weather information through the OpenWeatherMap API
- Display current temperature
- Display feels-like temperature
- Display humidity
- Display wind speed
- Display current weather conditions
- Display visual weather-condition symbols
- Display a short-term weather forecast
- Convert temperature between Celsius and Fahrenheit
- Convert wind speed between:
  - Meters per second
  - Kilometers per hour
  - Miles per hour
- Maintain a history of recent weather searches
- Display timestamps for searches
- Validate user input
- Handle invalid cities and API errors
- Change the application background according to the time of day

## Technologies

- Java 25
- JavaFX 21
- Maven
- Gson
- OpenWeatherMap API
- Visual Studio Code

## Project Structure

```text
WeatherInformationApp/
│
├── .gitignore
├── pom.xml
├── README.md
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── weatherapp/
│       │           ├── Main.java
│       │           └── WeatherAPI.java
│       │
│       └── resources/
│           └── images/
│
└── target/