package blueWeather.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CurrentLocationWeatherController extends WeatherBaseController {

    public CurrentLocationWeatherController() {
        super();
        getCurrentLocation();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpWeatherViews();
    }

    @Override
    protected void getLocation() {
        getCurrentLocation();
        clearAllViews();
        setUpWeatherViews();
    }

    private void getCurrentLocation() {
        try {
            this.location = locationHandler.getCurrentLocationByIp();
        } catch (IOException | InterruptedException e) {
            clearAllViews();
            generalError.setText("Sorry, your location could not be determined.");
        }
    }
}
