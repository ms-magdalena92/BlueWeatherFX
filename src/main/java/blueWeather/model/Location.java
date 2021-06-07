package blueWeather.model;

import com.google.gson.annotations.SerializedName;

public class Location {

    private String country;

    @SerializedName(value = "city", alternate = {"name"})
    private String city;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
