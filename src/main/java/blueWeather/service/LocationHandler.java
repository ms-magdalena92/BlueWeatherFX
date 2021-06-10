package blueWeather.service;

import blueWeather.App;
import blueWeather.model.Location;
import blueWeather.service.api.IpApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LocationHandler {

    private final String CITY_LIST_FILE_PATH = "json/city.list.min.json";

    public Location getCurrentLocationByIp() throws IOException, InterruptedException {
        IpApi ipApi = new IpApi();
        return ipApi.getLocationByIP();
    }

    public Map<String, String> getCityList() {
        return loadCityListJson();
    }

    private Map<String, String> loadCityListJson() {
        Type LOCATION_TYPE = new TypeToken<List<Location>>() {
        }.getType();

        Gson gson = new Gson();
        InputStreamReader inputReader = new InputStreamReader(Objects.requireNonNull(App.class.getResourceAsStream(CITY_LIST_FILE_PATH)), StandardCharsets.UTF_8);
        List<Location> cityList = gson.fromJson(inputReader, LOCATION_TYPE);

        return getCityListMap(cityList);
    }

    private Map<String, String> getCityListMap(List<Location> cityList) {
        Map<String, String> cityListWithCountryCode = new HashMap<>();

        for (Location location : cityList) {
            cityListWithCountryCode.put(location.getCity(), location.getCity() + ", " + location.getCountryCode());
        }

        return cityListWithCountryCode;
    }
}
