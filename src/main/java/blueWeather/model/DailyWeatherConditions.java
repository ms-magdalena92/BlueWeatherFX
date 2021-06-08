package blueWeather.model;

import net.aksingh.owmjapis.model.param.WeatherData;

public class DailyWeatherConditions extends WeatherBase {

    private double dayTemperature;

    private double nightTemperature;

    public DailyWeatherConditions(WeatherData dayWeather, double dayTemperature, double nightTemperature) {
        super(
                dayWeather.getDateTime(),
                dayWeather.getMainData().getPressure(),
                dayWeather.getWeatherList().get(0).getMainInfo(),
                dayWeather.getWeatherList().get(0).getIconLink()
        );
        this.dayTemperature = dayTemperature;
        this.nightTemperature = nightTemperature;
    }

    public String getTemperature() {
        return (int) dayTemperature + "\u00b0" + "/" + (int) nightTemperature + "\u00b0";
    }
}
