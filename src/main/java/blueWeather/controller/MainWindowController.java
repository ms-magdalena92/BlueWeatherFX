package blueWeather.controller;

import blueWeather.model.CurrentWeatherConditions;
import blueWeather.model.DailyWeatherConditions;
import blueWeather.service.WeatherForecastFetcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.aksingh.owmjapis.api.APIException;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    private static final String MAIN_VIEW_FILE_NAME = "main.fxml";

    private static final String DEFAULT_CITY = "Warsaw";

    CurrentWeatherConditions currentWeather;

    private final WeatherForecastFetcher weatherForecastFetcher;

    private List<DailyWeatherConditions> dailyWeatherForecast;

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
        weatherForecastFetcher = new WeatherForecastFetcher(DEFAULT_CITY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            currentWeather = weatherForecastFetcher.fetchCurrentWeatherForecast();
            dailyWeatherForecast = weatherForecastFetcher.fetchDailyWeatherForecast();
            if (currentWeather != null) {
                setUpCurrentWeatherView();
            } else {
                //show error: data cannot be fully downloaded
            }

            if (!dailyWeatherForecast.isEmpty()) {
                setUpExtendedForecastView();
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

    private void setUpExtendedForecastView() {
        for (DailyWeatherConditions dailyWeatherConditions : dailyWeatherForecast) {
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
