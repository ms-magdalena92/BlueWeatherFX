package blueWeather.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MainWindowController extends BaseController {

    private static final String MAIN_VIEW_FILE_NAME = "main.fxml";

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
    private Label feelsLikeTemperature;

    @FXML
    private Label humidity;

    @FXML
    private Label pressure;

    @FXML
    private Label wind;

    @FXML
    private AnchorPane targetLocationWeather;

    public MainWindowController() {
        super(MAIN_VIEW_FILE_NAME);
    }
}
