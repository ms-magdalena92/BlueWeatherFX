package blueWeather.model;

import com.google.gson.annotations.SerializedName;

public class Location {

    private String country;

    @SerializedName(value = "city", alternate = {"name"})
    private String city;

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
