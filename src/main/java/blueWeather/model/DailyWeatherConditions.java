package blueWeather.model;

import net.aksingh.owmjapis.model.param.WeatherData;

public class DailyWeatherConditions extends WeatherBase {

    private final int dayTemperature;

    private final int nightTemperature;

    public DailyWeatherConditions(WeatherData dayWeather, int dayTemperature, int nightTemperature) {
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
        return dayTemperature + "\u00b0" + "/" + nightTemperature + "\u00b0";
    }
}
