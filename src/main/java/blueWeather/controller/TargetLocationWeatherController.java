package blueWeather.controller;

import blueWeather.model.Location;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TargetLocationWeatherController extends WeatherBaseController implements Initializable {

    @FXML
    private TextField locationInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearAllViews();
        generalError.setText("Choose city");
        setUpLocationPicker();
    }

    @Override
    protected void getLocation() {
        getTargetLocation();
        clearAllViews();
        setUpWeatherViews();
    }

    private void setUpLocationPicker() {
        try {
            TextFields.bindAutoCompletion(locationInput, locationHandler.getCityList().values());
        } catch (IOException e) {
            generalError.setText("Sorry, list of available city could not be initialized.");
            e.printStackTrace();
        }
    }

    private void getTargetLocation() {
        location = new Location();
        location.setCityAndCountryCode(locationInput.getText());
    }
}
