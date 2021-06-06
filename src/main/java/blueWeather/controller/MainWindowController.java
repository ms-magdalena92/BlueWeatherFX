package blueWeather.controller;

import blueWeather.model.CurrentWeatherConditions;
import blueWeather.model.DailyWeatherConditions;
import blueWeather.model.Location;
import blueWeather.model.WeatherForecast;
import blueWeather.service.LocationHandler;
import blueWeather.service.WeatherForecastFetcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.aksingh.owmjapis.api.APIException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    private static final String MAIN_VIEW_FILE_NAME = "main.fxml";

    private Location location;

    private final WeatherForecastFetcher weatherForecastFetcher;

    private WeatherForecast weatherForecast;

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
    private HBox extendedForecast;

    public MainWindowController() {
        super(MAIN_VIEW_FILE_NAME);
        weatherForecastFetcher = new WeatherForecastFetcher();
        getCurrentLocation();

    }

    @FXML
    void getLocation() {
        getCurrentLocation();
        clearAllViews();
        setUpWeatherViews();
    }

    private void getCurrentLocation() {
        try {
            LocationHandler locationHandler = new LocationHandler();
            this.location = locationHandler.getCurrentLocationByIp();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void clearAllViews() {
        currentLocation.setText("");
        weatherIcon.setImage(null);
        currentTemperature.setText("");
        weatherDescription.setText("");
        humidity.setText("");
        pressure.setText("");
        wind.setText("");
        date.setText("");
        extendedForecast.getChildren().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpWeatherViews();
    }

    private void setUpWeatherViews() {
        if (location != null) {
            try {
                weatherForecast = weatherForecastFetcher.fetchWeatherForecast(location.getCity());

                if (weatherForecast.getCurrentWeatherConditions() != null) {
                    setUpCurrentWeatherView();
                } else {
                    //show error: data cannot be fully downloaded
                }

                if (!weatherForecast.getDailyWeatherConditions().isEmpty()) {
                    setUpExtendedForecastView();
                } else {
                    //show error: data cannot be fully downloaded
                }
            } catch (APIException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpCurrentWeatherView() {
        CurrentWeatherConditions currentWeather = weatherForecast.getCurrentWeatherConditions();

        currentLocation.setText(currentWeather.getCityName() + ", " + location.getCountry());
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

    private void setUpExtendedForecastView() {
        for (DailyWeatherConditions dailyWeatherConditions : weatherForecast.getDailyWeatherConditions()) {
            VBox dayVBox = new VBox();
            dayVBox.getChildren().addAll(
                    new Label(dailyWeatherConditions.getDate()),
                    new ImageView(new Image(dailyWeatherConditions.getIconUrl())),
                    new Label(dailyWeatherConditions.getDescription()),
                    new Label(dailyWeatherConditions.getTemperature()),
                    new Label(dailyWeatherConditions.getPressure())
            );

            extendedForecast.getChildren().add(dayVBox);
        }
    }
}
