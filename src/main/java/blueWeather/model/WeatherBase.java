package blueWeather.model;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class WeatherBase {

    protected Date date;

    private final double pressure;

    private final String description;

    private final String iconUrl;

    public WeatherBase(Date timestamp, double pressure, String description, String iconUrl) {
        this.date = timestamp;
        this.pressure = pressure;
        this.description = description;
        this.iconUrl = iconUrl;
    }

    public String getDate() {
        return DateTimeFormatter.ofPattern("E, MMM dd").toString();
    }

    public String getPressure() {
        return pressure + "hPa";
    }

    public String getDescription() {
        return description;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
