package blueWeather.service.api;

import blueWeather.config.Config;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.model.HourlyWeatherForecast;

public class OwmWeatherMapApi {

    private final OWM weatherMap;

    public OwmWeatherMapApi() {
        weatherMap = new OWM(Config.OWM_API_KEY);
        weatherMap.setUnit(OWM.Unit.METRIC);
    }

    public CurrentWeather getCurrentWeather(String cityName) throws APIException {
        return weatherMap.currentWeatherByCityName(cityName);
    }

    public HourlyWeatherForecast getHourlyWeather(String cityName) throws APIException {
        return weatherMap.hourlyWeatherForecastByCityName(cityName);
    }
}
