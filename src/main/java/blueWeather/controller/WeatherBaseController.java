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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.aksingh.owmjapis.api.APIException;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WeatherBaseController implements Initializable {

    protected Location location;

    private final WeatherForecastFetcher weatherForecastFetcher;

    protected final LocationHandler locationHandler;

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
    protected Label generalError;

    @FXML
    private Label currentWeatherError;

    @FXML
    private TextField locationInput;

    public WeatherBaseController() {
        weatherForecastFetcher = new WeatherForecastFetcher();
        locationHandler = new LocationHandler();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    protected void getLocation() {
        getTargetLocation();
        clearAllViews();
        setUpWeatherViews();
    }

    private void getTargetLocation() {
        location = new Location();
        location.setCityAndCountryCode(locationInput.getText());
    }

    protected void setUpLocationPicker() {
        try {
            TextFields.bindAutoCompletion(locationInput, locationHandler.getCityList().values());
        } catch (IOException e) {
            generalError.setText("Sorry, list of available city could not be initialized.");
            e.printStackTrace();
        }
    }

    protected void clearAllViews() {
        ObservableList<Node> panesChildren = FXCollections.concat(
                currentLocation.getParent().getChildrenUnmodifiable(),
                extendedForecast.getParent().getChildrenUnmodifiable()
        );
        setChildrenVisibility(panesChildren, false);
        generalError.setText("");
        currentWeatherError.setText("");
        extendedForecastError.setText("");
    }

    protected void setUpWeatherViews() {
        if (location != null) {
            try {
                weatherForecast = weatherForecastFetcher.fetchWeatherForecast(location.getCityAndCountryCode());
                showCurrentWeather();
                showExtendedForecast();
            } catch (APIException e) {
                clearAllViews();

                if (e.getCode() == 404 || e.getCode() == 400) {
                    generalError.setText("City not found.");
                } else {
                    generalError.setText("Sorry, something went wrong: API error.");
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
            extendedForecastError.setText("Data could not be fully downloaded.");
        }
    }

    private void showCurrentWeather() {
        if (weatherForecast.getCurrentWeatherConditions() != null) {
            setUpCurrentWeatherView();
        } else {
            setChildrenVisibility(currentLocation.getParent().getChildrenUnmodifiable(), false);
            currentWeatherError.setText("Data could not be fully downloaded.");
        }
    }

    private void setUpCurrentWeatherView() {
        CurrentWeatherConditions currentWeather = weatherForecast.getCurrentWeatherConditions();

        setChildrenVisibility(currentLocation.getParent().getChildrenUnmodifiable(), true);

        currentLocation.setText(location.getCityAndCountryCode());
        weatherIcon.setImage(new Image(currentWeather.getIconUrl()));
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

            Label dateLabel = new Label(dailyWeatherConditions.getDate());
            dateLabel.getStyleClass().add("dateLabel");
            Label descriptionLabel = new Label(dailyWeatherConditions.getDescription());
            descriptionLabel.getStyleClass().add("bolder");

            Label temperatureLabel = new Label(dailyWeatherConditions.getTemperature());
            temperatureLabel.getStyleClass().add("bolder");
            Label pressureLabel = new Label(dailyWeatherConditions.getPressure());
            temperatureLabel.getStyleClass().add("smaller");

            dayVBox.getChildren().addAll(
                    dateLabel,
                    new ImageView(new Image(dailyWeatherConditions.getIconUrl())),
                    descriptionLabel,
                    temperatureLabel,
                    pressureLabel
            );
            dayVBox.setAlignment(Pos.CENTER);

            extendedForecast.getChildren().add(dayVBox);
            extendedForecast.setSpacing(35);
        }
    }

    private void setChildrenVisibility(ObservableList<Node> children, boolean visible) {
        for (Node child : children) {
            child.setVisible(visible);
        }
    }
}
