package com.nsu.burym.config;

import com.nsu.burym.model.GeocodePage;
import com.nsu.burym.restServices.GetGeocodeService;
import com.nsu.burym.restServices.GetPlacesService;
import com.nsu.burym.restServices.GetWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Future;

@Configuration
@EnableAsync // Подключает возможность Spring запускать @Async методы в фоновом пуле потоков
@ComponentScan("com.nsu.burym")
@PropertySource("classpath:api.properties")
public class SpringConfig {

    private final Environment environment;
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(Environment environment, ApplicationContext applicationContext) {
        this.environment = environment;
        this.applicationContext = applicationContext;
    }

    @Bean
    public GetGeocodeService createGeocodeService() {
        GetGeocodeService getGeocodeService = new GetGeocodeService();
        getGeocodeService.setKey(environment.getProperty("geocoding_api_key"));
        return getGeocodeService;
    }

    @Bean
    public GetWeatherService createWeatherService() {
        GetWeatherService getWeatherService = new GetWeatherService();
        getWeatherService.setKey(environment.getProperty("weather_api_key"));
        return getWeatherService;
    }

    @Bean
    public GetPlacesService createPlacesService() {
        return new GetPlacesService();
    }
}