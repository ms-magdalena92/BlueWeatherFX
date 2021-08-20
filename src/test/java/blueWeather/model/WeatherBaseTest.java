package blueWeather.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherBaseTest {

    private static final double PRESSURE = 1013;

    private static final String DATE = "Mon, Jan 19";

    private WeatherBase weatherBase;

    @BeforeEach
    void setUp() {
        weatherBase = new WeatherBase(new Date(1629475989), PRESSURE, "test", "test");
    }

    @Test
    void shouldReturnDateInProperFormat() {
        //when
        //then
        assertEquals(weatherBase.getDate(), DATE);
    }

    @Test
    void shouldReturnPressureInProperFormat() {
        //when
        //then
        assertEquals(weatherBase.getPressure(), PRESSURE + "hPa");
    }
}
