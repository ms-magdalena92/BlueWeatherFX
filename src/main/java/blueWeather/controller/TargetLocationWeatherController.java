package blueWeather.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TargetLocationWeatherController extends WeatherBaseController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearAllViews();
        generalError.setText("Choose city");
    }
}
