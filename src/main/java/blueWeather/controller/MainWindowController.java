package blueWeather.controller;

import blueWeather.model.CurrentWeatherConditions;
import blueWeather.service.WeatherForecastFetcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.aksingh.owmjapis.api.APIException;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    private static final String MAIN_VIEW_FILE_NAME = "main.fxml";

    private static final String DEFAULT_CITY = "Warsaw";

    @FXML
    private AnchorPane currentLocationWeather;

    @FXML
    private Label currentLocation;

    @FXML
    private ImageView weatherIcon;

    @FXML
    private Label currentTemperature;

    @FXML
    private Label weatherDescription;

    @FXML
    private Label humidity;

    @FXML
    private Label pressure;

    @FXML
    private Label wind;

    @FXML
    private Label date;

    @FXML
    private AnchorPane targetLocationWeather;

    CurrentWeatherConditions currentWeather;

    private final WeatherForecastFetcher weatherForecastFetcher;

    public MainWindowController() {
        super(MAIN_VIEW_FILE_NAME);
        weatherForecastFetcher = new WeatherForecastFetcher(DEFAULT_CITY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            currentWeather = weatherForecastFetcher.fetchCurrentWeatherForecast();
            if (currentWeather != null) {
                setUpCurrentWeatherView();
            } else {
                //show error: data cannot be fully downloaded
            }
        } catch (APIException e) {
            e.printStackTrace();
        }
    }

    private void setUpCurrentWeatherView() {
        currentLocation.setText(currentWeather.getCityName());
        weatherIcon.setImage(new Image(currentWeather.getIconUrl()));
        weatherIcon.setFitHeight(60);
        weatherIcon.setFitWidth(60);
        currentTemperature.setText(currentWeather.getTemperature());
        weatherDescription.setText(currentWeather.getDescription());
        humidity.setText(currentWeather.getHumidity());
        pressure.setText(currentWeather.getPressure());
        wind.setText(currentWeather.getWindSpeed());
        date.setText(currentWeather.getDate());
    }
}
