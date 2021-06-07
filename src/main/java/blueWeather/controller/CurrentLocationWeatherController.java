package blueWeather.controller;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CurrentLocationWeatherController extends WeatherBaseController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getLocationByIp();
        setUpWeatherViews();
        setUpLocationPicker();
    }

    @FXML
    private void getCurrentLocation() {
        getLocationByIp();
        clearAllViews();
        setUpWeatherViews();
    }

    private void getLocationByIp() {
        try {
            this.location = locationHandler.getCurrentLocationByIp();
        } catch (IOException | InterruptedException e) {
            clearAllViews();
            generalError.setText("Sorry, your location could not be determined.");
        }
    }
}
