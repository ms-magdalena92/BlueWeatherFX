package blueWeather.service.api;

import blueWeather.model.Location;
import com.google.gson.Gson;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IpApiTest {

    @InjectMocks
    private IpApi ipApi;

    @Mock
    private HttpClient client;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private Gson gson;

    @Test
    @Disabled
    void shouldReturnLocationFromJsonResponse() throws IOException, InterruptedException {

        //given
        Gson gson = new Gson();
        HttpResponse<String> httpResponse = mock(HttpResponse.class);
        Location location = new Location("PL", "Krakow", null);

        given(httpResponse.body()).willReturn(gson.toJson(location));
        given(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).willReturn(httpResponse);

        //when
        //then
        assertEquals(ipApi.getLocationByIP(), location);
    }
}
