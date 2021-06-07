module blueWeather {
    requires javafx.controls;
    requires javafx.fxml;
    requires owm.japis;
    requires java.sql;
    requires java.net.http;
    requires com.google.gson;
    requires org.controlsfx.controls;

    opens blueWeather to javafx.fxml;
    exports blueWeather;
    opens blueWeather.controller to javafx.fxml;
}
