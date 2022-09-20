package com.example.weather.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class WeatherForecastDto {

    private int temp;
    private int humidity;

}
