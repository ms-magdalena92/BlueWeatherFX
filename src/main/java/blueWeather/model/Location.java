package blueWeather.model;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName(value = "countryCode", alternate = {"country"})
    private String countryCode;

    @SerializedName(value = "city", alternate = {"name"})
    private String city;

    private String cityAndCountryCode = null;

    public Location() {
    }

    public Location(String countryCode, String city, String cityAndCountryCode) {
        this.countryCode = countryCode;
        this.city = city;
        this.cityAndCountryCode = cityAndCountryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCity() {
        return city;
    }

    public String getCityAndCountryCode() {
        return cityAndCountryCode != null ? cityAndCountryCode : city + ", " + countryCode;
    }

    public void setCityAndCountryCode(String cityAndCountryCode) {
        this.cityAndCountryCode = cityAndCountryCode;
    }
}
