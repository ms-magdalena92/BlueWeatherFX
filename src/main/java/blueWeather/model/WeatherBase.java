package blueWeather.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM dd", new Locale("en", "EN"));
        return dateFormat.format(date);
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
