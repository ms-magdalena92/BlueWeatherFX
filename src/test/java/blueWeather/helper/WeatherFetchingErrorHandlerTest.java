package blueWeather.helper;


import blueWeather.Messages;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherFetchingErrorHandlerTest {

    @ParameterizedTest
    @MethodSource("provideErrorCode")
    void shouldReturnProperMessageDependingOnTheErrorCode(int errorCode, String expected) {
        //when
        //then
        assertEquals(WeatherFetchingErrorHandler.handle(errorCode), expected);
    }

    private static Stream<Arguments> provideErrorCode() {
        return Stream.of(
                Arguments.of(400, Messages.BAD_API_REQUEST),
                Arguments.of(404, Messages.CITY_NOT_FOUND),
                Arguments.of(401, Messages.API_ERROR),
                Arguments.of(403, Messages.API_ERROR)
        );
    }
}
