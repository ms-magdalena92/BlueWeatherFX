package blueWeather.service;

import blueWeather.model.Location;
import blueWeather.service.api.IpApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationHandler {

    private final String CITY_LIST_FILE_PATH = "src/main/resources/blueWeather/json/city.list.min.json";

    public Location getCurrentLocationByIp() throws IOException, InterruptedException {
        IpApi ipApi = new IpApi();
        return ipApi.getLocationByIP();
    }

    public Map<String, String> getCityList() throws IOException {
        return loadCityListJson();
    }

    private Map<String, String> loadCityListJson() throws IOException {
        Type LOCATION_TYPE = new TypeToken<List<Location>>() {
        }.getType();

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader(CITY_LIST_FILE_PATH, StandardCharsets.UTF_8));
        List<Location> cityList = gson.fromJson(jsonReader, LOCATION_TYPE);

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
