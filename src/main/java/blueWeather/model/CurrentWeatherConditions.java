package blueWeather.model;

import net.aksingh.owmjapis.model.CurrentWeather;

public class CurrentWeatherConditions extends WeatherBase {

    private final int temperature;

    private final int humidity;

    private final int windSpeed;

    private final String cityName;

    public CurrentWeatherConditions(CurrentWeather currentWeather) {
        super(
                currentWeather.getDateTime(),
                currentWeather.getMainData().getPressure(),
                currentWeather.getWeatherList().get(0).getDescription(),
                currentWeather.getWeatherList().get(0).getIconLink()
        );
        temperature = (int) Math.round(currentWeather.getMainData().getTemp());
        humidity = (int) Math.round(currentWeather.getMainData().getHumidity());
        windSpeed = (int) Math.round(currentWeather.getWindData().getSpeed());
        cityName = currentWeather.getCityName();
    }

    public String getTemperature() {
        return temperature + "\u00b0";
    }

    public String getHumidity() {
        return humidity + "%";
    }

    public String getWindSpeed() {
        return windSpeed + "mps";
    }

    public String getCityName() {
        return cityName;
    }
}
