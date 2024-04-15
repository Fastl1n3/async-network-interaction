package com.nsu.burym.restServices;


import com.nsu.burym.model.GeocodePage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

public class GetGeocodeService {
    //String q - название места
    //String locale - ставить ru
    private String key;
    RestTemplate restTemplate = new RestTemplate();

    public void setKey(String key) {
        this.key = key;
    }
    @Async
    public Future<GeocodePage> findPage(String place) throws InterruptedException {
        GeocodePage results = restTemplate.getForObject("https://graphhopper.com/api/1/geocode?q="
                + place + "&locale=ru&key="+ key, GeocodePage.class);
      //  Thread.sleep(1000L);1
        System.out.println("COOL");
        return new AsyncResult<GeocodePage>(results);
    }
}
