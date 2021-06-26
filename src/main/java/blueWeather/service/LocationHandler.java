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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class LocationHandler {

    private final IpApi ipApi;

    private final Gson gson;

    public LocationHandler(Gson gson, IpApi ipApi) {
        this.gson = gson;
        this.ipApi = ipApi;
    }

    private static final String CITY_LIST_FILE_PATH = "json/city.list.min.json";

    public Location getCurrentLocationByIp() throws IOException, InterruptedException {
        return ipApi.getLocationByIP();
    }

    public Map<String, String> getCityList() {
        return loadCityListJson();
    }

    private Map<String, String> loadCityListJson() {
        Type LOCATION_TYPE = new TypeToken<List<Location>>() {
        }.getType();

        InputStreamReader inputReader = new InputStreamReader(Objects.requireNonNull(App.class.getResourceAsStream(CITY_LIST_FILE_PATH)), StandardCharsets.UTF_8);
        List<Location> cityList = gson.fromJson(inputReader, LOCATION_TYPE);

        return getCityListMap(cityList);
    }

    private Map<String, String> getCityListMap(List<Location> cityList) {
        return cityList.stream()
                .collect(Collectors.toMap(
                        Location::getCity,
                        location -> location.getCity() + ", " + location.getCountryCode(),
                        (l1, l2) -> l1
                ));
    }
}
