module blueWeather {
    requires javafx.controls;
    requires javafx.fxml;

    opens blueWeather to javafx.fxml;
    exports blueWeather;
}
