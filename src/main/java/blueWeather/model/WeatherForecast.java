package blueWeather.model;

import java.util.List;

public class WeatherForecast {

    private final CurrentWeatherConditions currentWeatherConditions;

    private final List<DailyWeatherConditions> dailyWeatherConditions;

    public WeatherForecast(CurrentWeatherConditions currentWeatherConditions, List<DailyWeatherConditions> dailyWeatherConditions) {
        this.currentWeatherConditions = currentWeatherConditions;
        this.dailyWeatherConditions = dailyWeatherConditions;
    }

    public CurrentWeatherConditions getCurrentWeatherConditions() {
        return currentWeatherConditions;
    }

    public List<DailyWeatherConditions> getDailyWeatherConditions() {
        return dailyWeatherConditions;
    }
}
