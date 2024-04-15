package com.nsu.burym.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) // игнорировать любые атрибуты не определ в классе
public class GeocodePage {
    private List<GeocodingLocation> hits;

}
