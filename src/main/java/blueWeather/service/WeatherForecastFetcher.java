package blueWeather.service;

import blueWeather.model.CurrentWeatherConditions;
import blueWeather.model.DailyWeatherConditions;
import blueWeather.model.WeatherForecast;
import blueWeather.service.api.OwmWeatherMapApi;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.model.HourlyWeatherForecast;
import net.aksingh.owmjapis.model.param.WeatherData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class WeatherForecastFetcher {

    private static final int WEATHER_FORECAST_DAYS_COUNT = 4;

    private final OwmWeatherMapApi weatherApi;

    public WeatherForecastFetcher(OwmWeatherMapApi owmWeatherMapApi) {
        weatherApi = owmWeatherMapApi;
    }

    public WeatherForecast fetchWeatherForecast(String cityName) throws APIException {
        return new WeatherForecast(fetchCurrentWeatherForecast(cityName), fetchDailyWeatherForecast(cityName));
    }

    private CurrentWeatherConditions fetchCurrentWeatherForecast(String cityName) throws APIException {
        CurrentWeather currentWeather = weatherApi.getCurrentWeather(cityName);
        CurrentWeatherConditions currentWeatherConditions = null;

        if (currentWeather.hasRespCode() && currentWeather.getRespCode() == 200) {
            currentWeatherConditions = new CurrentWeatherConditions(currentWeather);
        }

        return currentWeatherConditions;
    }

    private List<DailyWeatherConditions> fetchDailyWeatherForecast(String cityName) throws APIException {
        HourlyWeatherForecast hourlyWeatherForecast = weatherApi.getHourlyWeather(cityName);
        List<DailyWeatherConditions> dailyWeatherForecast = new ArrayList<>();

        if (hourlyWeatherForecast.hasRespCode() && hourlyWeatherForecast.getRespCode().equals("200")) {
            double dayTemperature = 0;
            double nightTemperature;
            WeatherData dayWeather = null;

            for (WeatherData weatherData : hourlyWeatherForecast.getDataList()) {
                Date date = weatherData.getDateTime();

                if (getDayOfWeek(new Date()) != getDayOfWeek(date)) {
                    int hour = getHour(date);

                    if (isDayTemperature(hour)) {
                        dayTemperature = weatherData.getMainData().getTemp();
                        dayWeather = weatherData;
                    }

                    if (isNightTemperature(hour) && dayTemperature != 0) {
                        nightTemperature = weatherData.getMainData().getTemp();
                        DailyWeatherConditions dailyWeatherConditions = new DailyWeatherConditions(dayWeather,
                                (int) Math.round(dayTemperature), (int) Math.round(nightTemperature));
                        dailyWeatherForecast.add(dailyWeatherConditions);
                        dayTemperature = 0;
                    }

                    if (dailyWeatherForecast.size() == WEATHER_FORECAST_DAYS_COUNT) {
                        break;
                    }
                }
            }
        }

        return dailyWeatherForecast;
    }

    private boolean isNightTemperature(int hour) {
        return hour > 21;
    }

    private boolean isDayTemperature(int hour) {
        return hour > 12 && hour <= 15;
    }

    private DayOfWeek getDayOfWeek(Date date) {
        LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDate.getDayOfWeek();
    }

    private int getHour(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.getHour();
    }
}
