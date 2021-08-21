package blueWeather.service.api;

import blueWeather.model.Location;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IpApi {

    private static final String BASE_API_URL = "http://ip-api.com/json";

    private final Gson gson;

    private final HttpClient client;

    public IpApi(Gson gson, HttpClient client) {
        this.gson = gson;
        this.client = client;
    }

    public Location getLocationByIP() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder(
                URI.create(BASE_API_URL))
                .header("accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), Location.class);
    }
}
