package blueWeather.model;

import net.aksingh.owmjapis.model.CurrentWeather;

public class CurrentWeatherConditions extends WeatherBase {

    private final double temperature;

    private final double humidity;

    private final double windSpeed;

    private final String cityName;

    public CurrentWeatherConditions(CurrentWeather currentWeather) {
        super(
                currentWeather.getDateTime(),
                currentWeather.getMainData().getPressure(),
                currentWeather.getWeatherList().get(0).getDescription(),
                currentWeather.getWeatherList().get(0).getIconLink()
        );
        temperature = currentWeather.getMainData().getTemp();
        humidity = currentWeather.getMainData().getHumidity();
        windSpeed = currentWeather.getWindData().getSpeed();
        cityName = currentWeather.getCityName();
    }

    public String getTemperature() {
        return (int) temperature + "\u00b0C";
    }

    public String getHumidity() {
        return (int) humidity + "%";
    }

    public String getWindSpeed() {
        return (int) windSpeed + "mps";
    }

    public String getCityName() {
        return cityName;
    }
}
