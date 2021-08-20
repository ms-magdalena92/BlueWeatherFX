package blueWeather.service;

import blueWeather.model.CurrentWeatherConditions;
import blueWeather.model.DailyWeatherConditions;
import blueWeather.model.WeatherForecast;
import blueWeather.service.api.OwmWeatherMapApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class WeatherForecastFetcherTest {

    @InjectMocks
    private WeatherForecastFetcher weatherForecastFetcher;

    @Mock
    private OwmWeatherMapApi weatherApi;

    @Test
    public void shouldReturnWeatherForecastWithValues() throws Exception {
        //given
        String cityName = "example";
        int apiResponseCode = 200;

        given(weatherApi.getCurrentWeather(cityName)).willReturn(OwmApiStub.getCurrentWeather(apiResponseCode,
                cityName));
        given(weatherApi.getHourlyWeather(cityName)).willReturn(OwmApiStub.getHourlyWeatherForecast(apiResponseCode));

        //when
        WeatherForecast weatherForecast = weatherForecastFetcher.fetchWeatherForecast(cityName);
        CurrentWeatherConditions currentWeatherConditions = weatherForecast.getCurrentWeatherConditions();
        List<DailyWeatherConditions> dailyWeatherConditions = weatherForecast.getDailyWeatherConditions();

        //then
        assertThat(dailyWeatherConditions, hasSize(4));
        assertThat(currentWeatherConditions.getCityName(), equalTo(cityName));
        assertThat(currentWeatherConditions.getHumidity(), equalTo((int) Math.round(OwmApiStub.HUMIDITY) + "%"));
        assertThat(dailyWeatherConditions.get(0).getPressure(), equalTo(OwmApiStub.PRESSURE + "hPa"));
    }

    @Test
    public void shouldReturnWeatherForecastWithNoValues() throws Exception {
        //given
        String cityName = "example";
        int apiResponseCode = 400;

        given(weatherApi.getCurrentWeather(cityName)).willReturn(OwmApiStub.getCurrentWeather(apiResponseCode,
                cityName));
        given(weatherApi.getHourlyWeather(cityName)).willReturn(OwmApiStub.getHourlyWeatherForecast(apiResponseCode));

        //when
        WeatherForecast weatherForecast = weatherForecastFetcher.fetchWeatherForecast(cityName);

        //then
        assertThat(weatherForecast.getCurrentWeatherConditions(), nullValue());
        assertThat(weatherForecast.getDailyWeatherConditions(), hasSize(0));
    }
}
