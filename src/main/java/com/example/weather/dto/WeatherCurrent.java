package com.example.weather.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
public class WeatherCurrent{

    private List<WeatherForecastMainDayDto> list;
    private CityName city;


}