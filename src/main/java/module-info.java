module blueWeather {
    requires javafx.controls;
    requires javafx.fxml;

    opens blueWeather to javafx.fxml;
    exports blueWeather;
    opens blueWeather.controller to javafx.fxml;
}
