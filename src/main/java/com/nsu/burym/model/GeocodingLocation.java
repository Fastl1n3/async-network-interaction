package com.nsu.burym.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingLocation {
    private GeocodingPoint point;
    private String osm_key; //что за объект
    private String name;
    private String country;
    private String state; //область
    private String city;

}
