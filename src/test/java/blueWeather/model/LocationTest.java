package blueWeather.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

    @Test
    void shouldReturnCityAndCountryCodeWhenItsValueIsNull() {
        //given
        Location location = new Location("code", "city", null);

        //when
        //then
        assertEquals(location.getCityAndCountryCode(), "city, code");
    }
}
