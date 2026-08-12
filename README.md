# Weather Information App

A JavaFX desktop application that retrieves and displays current weather information and short-term forecasts using the OpenWeather API.

## Features

- Search weather information by city
- Display current temperature
- Display weather description
- Display feels-like temperature
- Display humidity
- Display wind speed
- Display weather icons
- Display short-term weather forecasts
- Celsius and Fahrenheit temperature units
- Meters/second, miles/hour, and kilometers/hour wind units
- Recent city searches
- Error handling for invalid cities
- Error handling for invalid or inactive API keys
- JavaFX graphical user interface

## Technologies Used

- Java 25
- JavaFX 21
- Maven 3.9.16
- Gson
- OpenWeather API
- Visual Studio Code
- Git and GitHub

## Project Structure

```text
WeatherInformationApp
|
+-- .gitignore
+-- pom.xml
+-- README.md
|
+-- src
|   +-- main
|       +-- java
|       |   +-- com
|       |       +-- weatherapp
|       |           +-- Main.java
|       |           +-- WeatherAPI.java
|       |
|       +-- resources
|           +-- images
|
+-- target
    +-- Generated automatically by Maven