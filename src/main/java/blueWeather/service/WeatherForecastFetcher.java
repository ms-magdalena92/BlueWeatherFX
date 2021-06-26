package blueWeather.service;

import blueWeather.model.CurrentWeatherConditions;
import blueWeather.model.DailyWeatherConditions;
import blueWeather.model.WeatherForecast;
import blueWeather.service.api.OwmWeatherMapApi;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.model.HourlyWeatherForecast;
import net.aksingh.owmjapis.model.param.WeatherData;

import java.util.*;

public class WeatherForecastFetcher {

    private final OwmWeatherMapApi weatherApi;

    public WeatherForecastFetcher() {
        weatherApi = new OwmWeatherMapApi();
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

                    if (hour > 12 && hour <= 15) {
                        dayTemperature = weatherData.getMainData().getTemp();
                        dayWeather = weatherData;
                    }

                    if (hour > 21 && dayTemperature != 0) {
                        nightTemperature = weatherData.getMainData().getTemp();
                        DailyWeatherConditions dailyWeatherConditions = new DailyWeatherConditions(dayWeather,
                                dayTemperature, nightTemperature);
                        dailyWeatherForecast.add(dailyWeatherConditions);
                        dayTemperature = 0;
                    }

                    if (dailyWeatherForecast.size() == 4) {
                        break;
                    }
                }
            }
        }

        return dailyWeatherForecast;
    }

    private int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
