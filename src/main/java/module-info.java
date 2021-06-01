module blueWeather {
    requires javafx.controls;
    requires javafx.fxml;
    requires owm.japis;
    requires java.sql;

    opens blueWeather to javafx.fxml;
    exports blueWeather;
    opens blueWeather.controller to javafx.fxml;
}
