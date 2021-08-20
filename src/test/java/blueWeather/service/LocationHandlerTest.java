package blueWeather.service;

import blueWeather.model.Location;
import blueWeather.service.api.IpApi;
import com.google.gson.Gson;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class LocationHandlerTest {

    @InjectMocks
    private LocationHandler locationHandler;

    @Mock
    private Gson gson;

    @Mock
    private IpApi ipApi;

    @Test
    public void getCurrentLocationByIpShouldReturnLocation() throws IOException, InterruptedException {
        //given
        given(ipApi.getLocationByIP()).willReturn(new Location());

        //when
        locationHandler.getCurrentLocationByIp();

        //then
        then(ipApi).should().getLocationByIP();
    }

    @Test
    public void getCityListShouldReturnNotEmptyMap() {
        //given
        Location location1 = new Location();
        location1.setCity("City1");
        location1.setCityAndCountryCode("City1, Code1");
        location1.setCountryCode("Code1");

        Location location2 = new Location();
        location2.setCity("City2");
        location2.setCityAndCountryCode("City2, Code2");
        location2.setCountryCode("Code2");

        given(gson.fromJson(any(InputStreamReader.class), any(Type.class))).willReturn(List.of(location1, location2));

        //when
        Map<String, String> cityList =  locationHandler.getCityList();

        //then
        assertThat(cityList.size(), is(2));
        assertThat(cityList, IsMapContaining.hasEntry(location1.getCity(), location1.getCityAndCountryCode()));
        assertThat(cityList, IsMapContaining.hasEntry(location2.getCity(), location2.getCityAndCountryCode()));
    }
}