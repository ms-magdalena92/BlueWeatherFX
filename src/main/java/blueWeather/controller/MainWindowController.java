package blueWeather.controller;

import blueWeather.model.CurrentWeatherConditions;
import blueWeather.model.DailyWeatherConditions;
import blueWeather.model.Location;
import blueWeather.model.WeatherForecast;
import blueWeather.service.LocationHandler;
import blueWeather.service.WeatherForecastFetcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    @FXML
    private Label extendedForecastError;

    @FXML
    private Label generalError;

    @FXML
    private Label currentWeatherError;

    public MainWindowController() {
        super(MAIN_VIEW_FILE_NAME);
        weatherForecastFetcher = new WeatherForecastFetcher();
        getCurrentLocation();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpWeatherViews();
    }

    @FXML
    private void getLocation() {
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
        ObservableList<Node> panesChildren = FXCollections.concat(
                currentLocation.getParent().getChildrenUnmodifiable(),
                extendedForecast.getParent().getChildrenUnmodifiable()
        );
        setChildrenVisibility(panesChildren, false);
        generalError.setText("");
        currentWeatherError.setText("");
        extendedForecastError.setText("");
    }

    private void setUpWeatherViews() {
        if (location != null) {
            try {
                weatherForecast = weatherForecastFetcher.fetchWeatherForecast(location.getCity());
                showCurrentWeather();
                showExtendedForecast();
            } catch (APIException e) {
                clearAllViews();

                if (e.getCode() == 404 || e.getCode() == 400) {
                    generalError.setText("City not found.");
                } else {
                    generalError.setText("Sorry, something went wrong.");
                }

                e.printStackTrace();
            }
        }
    }

    private void showExtendedForecast() {
        if (!weatherForecast.getDailyWeatherConditions().isEmpty()) {
            setUpExtendedForecastView();
        } else {
            extendedForecast.setVisible(false);
            extendedForecastError.setText("Sorry, data could not be fully downloaded.");
        }
    }

    private void showCurrentWeather() {
        if (weatherForecast.getCurrentWeatherConditions() != null) {
            setUpCurrentWeatherView();
        } else {
            setChildrenVisibility(currentLocation.getParent().getChildrenUnmodifiable(), false);
            currentWeatherError.setText("Sorry, data could not be fully downloaded.");
        }
    }

    private void setUpCurrentWeatherView() {
        CurrentWeatherConditions currentWeather = weatherForecast.getCurrentWeatherConditions();

        setChildrenVisibility(currentLocation.getParent().getChildrenUnmodifiable(), true);

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
        extendedForecast.getChildren().clear();
        extendedForecast.setVisible(true);

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

    private void setChildrenVisibility(ObservableList<Node> children, boolean visible) {
        for (Node child : children) {
            child.setVisible(visible);
        }
    }
}
