package blueWeather.service;

import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.model.HourlyWeatherForecast;
import net.aksingh.owmjapis.model.param.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class OwmApiStub {

    public static Double pressure = 1015.0;

    public static Double humidity = 80.0;

    public static Double temperature = 19.89;

    public static Integer weatherListSize = 40;

    public static Integer conditionId = 801;

    public static Double windSpeed = 1.54;

    public static String mainInfo = "Clouds";

    public static String iconCode = "02d";

    public static CurrentWeather getCurrentWeather(int responseCode, String cityName) {
        return new CurrentWeather(getIntDatetime(getCurrentDatetime()), null, null, null, List.of(getWeather()), cityName, responseCode,
                getMainData(), null, null, null, null, new Wind(windSpeed, null, null));
    }

    public static HourlyWeatherForecast getHourlyWeatherForecast(int responseCode) {
        return new HourlyWeatherForecast(Integer.toString(responseCode), null, null, weatherListSize, getWeatherDataList());
    }

    private static LocalDateTime getCurrentDatetime() {
        return LocalDateTime.now();
    }

    private static int getIntDatetime(LocalDateTime dateTime) {
        return (int) dateTime.toEpochSecond(ZoneOffset.UTC);
    }

    private static WeatherData getWeatherData(int timestamp) {
        return new WeatherData(timestamp, getMainData(), null, pressure, humidity, List.of(getWeather()), null,
                null, null, null);
    }

    private static Weather getWeather() {
        return new Weather(conditionId, mainInfo, null, iconCode);
    }

    private static Main getMainData() {
        return new Main(temperature, null, null, pressure, null,
                null, humidity, null);
    }

    private static List<WeatherData> getWeatherDataList() {

        int i = 1;
        List<WeatherData> list = new ArrayList<>();
        LocalDateTime dateTime = getCurrentDatetime();

        while (i <= weatherListSize) {
            list.add(getWeatherData(getIntDatetime(dateTime)));
            dateTime = dateTime.plusHours(3);
            i++;
        }

        return list;
    }
}
