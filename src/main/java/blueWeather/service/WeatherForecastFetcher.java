package blueWeather.service;

import blueWeather.model.CurrentWeatherConditions;
import blueWeather.service.api.OwmWeatherMapApi;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.CurrentWeather;

public class WeatherForecastFetcher {

    private final String cityName;

    private final OwmWeatherMapApi weatherApi;

    public WeatherForecastFetcher(String cityName) {
        this.cityName = cityName;
        weatherApi = new OwmWeatherMapApi();
    }

    public CurrentWeatherConditions fetchCurrentWeatherForecast() throws APIException {
        CurrentWeather currentWeather = weatherApi.getCurrentWeather(cityName);
        CurrentWeatherConditions currentWeatherConditions = null;

        if (currentWeather.hasRespCode() && currentWeather.getRespCode() == 200) {
            currentWeatherConditions = new CurrentWeatherConditions(currentWeather);
        }

        return currentWeatherConditions;
    }
}
