package blueWeather.service;

import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.model.HourlyWeatherForecast;
import net.aksingh.owmjapis.model.param.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class OwmApiStub {

    public static final String PRESSURE = "1015.0";

    public static final String HUMIDITY = "80";

    public static final Double TEMPERATURE = 19.89;

    public static final Integer WEATHER_LIST_SIZE = 40;

    public static final Integer CONDITION_ID = 801;

    public static final Double WIND_SPEED = 1.54;

    public static final String MAIN_INFO = "Clouds";

    public static final String ICON_CODE = "02d";

    public static CurrentWeather getCurrentWeather(int responseCode, String cityName) {
        return new CurrentWeather(getIntDatetime(getCurrentDatetime()), null, null, null, List.of(getWeather()), cityName, responseCode,
                getMainData(), null, null, null, null, new Wind(WIND_SPEED, null, null));
    }

    public static HourlyWeatherForecast getHourlyWeatherForecast(int responseCode) {
        return new HourlyWeatherForecast(Integer.toString(responseCode), null, null, WEATHER_LIST_SIZE, getWeatherDataList());
    }

    private static LocalDateTime getCurrentDatetime() {
        return LocalDateTime.now();
    }

    private static int getIntDatetime(LocalDateTime dateTime) {
        return (int) dateTime.toEpochSecond(ZoneOffset.UTC);
    }

    private static WeatherData getWeatherData(int timestamp) {
        return new WeatherData(timestamp, getMainData(), null, Double.parseDouble(PRESSURE),
                Double.parseDouble(HUMIDITY), List.of(getWeather()), null,
                null, null, null);
    }

    private static Weather getWeather() {
        return new Weather(CONDITION_ID, MAIN_INFO, null, ICON_CODE);
    }

    private static Main getMainData() {
        return new Main(TEMPERATURE, null, null, Double.parseDouble(PRESSURE), null,
                null, Double.parseDouble(HUMIDITY), null);
    }

    private static List<WeatherData> getWeatherDataList() {

        int i = 1;
        List<WeatherData> list = new ArrayList<>();
        LocalDateTime dateTime = getCurrentDatetime();

        while (i <= WEATHER_LIST_SIZE) {
            list.add(getWeatherData(getIntDatetime(dateTime)));
            dateTime = dateTime.plusHours(3);
            i++;
        }

        return list;
    }
}
