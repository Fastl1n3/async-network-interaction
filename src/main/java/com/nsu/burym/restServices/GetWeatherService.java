package com.nsu.burym.restServices;

import com.nsu.burym.model.GeocodePage;
import com.nsu.burym.model.WeatherPage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

public class GetWeatherService {
    private String key;
    RestTemplate restTemplate = new RestTemplate();

    public void setKey(String key) {
        this.key = key;
    }
    @Async
    public Future<WeatherPage> findWeather(double lat, double lon) throws InterruptedException {

        WeatherPage results = restTemplate.getForObject("https://api.openweathermap.org/data/2.5/weather?lat=" +
                lat +"&lon=" + lon + "&units=metric&&lang=ru" +
                "&appid=" + key, WeatherPage.class);
        // Thread.sleep(1000L);
        return new AsyncResult<WeatherPage>(results);
    }
}
