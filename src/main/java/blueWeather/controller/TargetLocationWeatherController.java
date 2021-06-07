package blueWeather.controller;

import java.net.URL;
import java.util.ResourceBundle;

public class TargetLocationWeatherController extends WeatherBaseController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearAllViews();
        generalError.setText("Choose city");
        setUpLocationPicker();
    }
}
