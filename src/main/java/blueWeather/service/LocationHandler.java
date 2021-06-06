package blueWeather.service;

import blueWeather.model.Location;
import blueWeather.service.api.IpApi;

import java.io.IOException;

public class LocationHandler {

    public Location getCurrentLocationByIp() throws IOException, InterruptedException {
        IpApi ipApi = new IpApi();
        return ipApi.getLocationByIP();
    }
}
