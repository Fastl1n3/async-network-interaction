package com.nsu.burym;

import com.nsu.burym.config.SpringConfig;
import com.nsu.burym.model.*;
import com.nsu.burym.myExceptions.LocationsNotFoundException;
import com.nsu.burym.restServices.GetGeocodeService;
import com.nsu.burym.restServices.GetPlacesService;
import com.nsu.burym.restServices.GetWeatherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@SpringBootApplication
public class App extends SpringBootServletInitializer {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringConfig.class, args);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите локацию:");
            Future<GeocodePage> geocodePage = context.getBean(GetGeocodeService.class).findPage(scanner.nextLine());
            System.out.println("WAIT");
            printLocations(geocodePage.get());

            int number = scanner.nextInt();
            GeocodingLocation geocodingLocation = geocodePage.get().getHits().get(number - 1);
            System.out.println("COORDS: " + geocodingLocation.getPoint().getLat() + " " + geocodingLocation.getPoint().getLng());

            Future<WeatherPage> weatherPage = context.getBean(GetWeatherService.class)
                    .findWeather(geocodingLocation.getPoint().getLat(), geocodingLocation.getPoint().getLng());
            Future<PlacesPage> placesPage = context.getBean(GetPlacesService.class)
                    .findPlaces(geocodingLocation.getPoint().getLat(), geocodingLocation.getPoint().getLng());

            printInfo(weatherPage, placesPage);
        } catch (LocationsNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            SpringApplication.exit(context);
        }
    }

    private static void printInfo(Future<WeatherPage> weatherPage, Future<PlacesPage> placesPage) throws ExecutionException, InterruptedException {
        printWeather(weatherPage.get());
        printPlaces(placesPage.get());
    }

    private static void printPlaces(PlacesPage placesPage) {
        List<Place> places = placesPage.getResults();
        System.out.println("МЕСТА:");
        for (Place place: places) {
            System.out.println("\"" + place.getTitle() + "\". Описание: " + place.getDescription());
            System.out.println("Адрес: " + place.getAddress());
            System.out.println();
        }
        if (places.size() == 0) {
            System.out.println("нет интересных мест.");
        }
    }


    private static void printWeather(WeatherPage weatherPage) {
        double degreesC = weatherPage.getMain().getTemp();
        System.out.println("Текущая температура: " +  degreesC + " *C");
        degreesC = weatherPage.getMain().getFeels_like();
        System.out.println("Ощущается как: " + degreesC + " *C");
        System.out.println();
    }

    public static void printLocations(GeocodePage geocodePage) throws LocationsNotFoundException {
        List<GeocodingLocation> geocodingLocations = geocodePage.getHits();
        if (geocodingLocations.size() == 0) {
            throw new LocationsNotFoundException("По этому месту ничего не найдено.");
        }
        int i = 1;
        System.out.println("Вот что удалось найти: ");
        for (GeocodingLocation location: geocodingLocations) {
            System.out.println(i + ". " + location.getName() + ", " +
                    (location.getCity() != null ? location.getCity()+ ", " : "")  + (location.getState() != null ? location.getState() : "") + ";");
            i++;
        }
        System.out.println();
        System.out.println("Введите номер желамого места:");
    }
}