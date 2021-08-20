package blueWeather.helper;

import blueWeather.Messages;

public class WeatherFetchingErrorHandler {
    public static String handle(int errorCode) {

        if (errorCode == 404) {
            return Messages.CITY_NOT_FOUND;
        }

        if (errorCode == 400) {
            return Messages.BAD_API_REQUEST;
        }

        return Messages.API_ERROR;
    }
}
