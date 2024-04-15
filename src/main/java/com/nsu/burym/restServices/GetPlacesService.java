package com.nsu.burym.restServices;

import com.nsu.burym.model.PlacesPage;
import com.nsu.burym.model.WeatherPage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

public class GetPlacesService {

    RestTemplate restTemplate = new RestTemplate();

    @Async
    public Future<PlacesPage> findPlaces(double lat, double lon) throws InterruptedException {

        PlacesPage results = restTemplate.getForObject("https://kudago.com/public-api/v1.3/search/?q=место&ctype=place" +
                "&lat=" + lat + "&lon=" + lon +"&radius=5000", PlacesPage.class);
        // Thread.sleep(1000L);
        return new AsyncResult<PlacesPage>(results);
    }

}
