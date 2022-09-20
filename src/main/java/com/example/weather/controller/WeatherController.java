package com.example.weather.controller;


import com.example.weather.dto.WeatherCurrent;
import com.example.weather.dto.WeatherCurrentRequestDto;
import com.example.weather.entity.WeatherEntity;
import com.example.weather.service.WeatherService;
import com.example.weather.service.WeatherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class WeatherController {

    @Autowired
    private final WeatherServiceImpl weatherServiceImpl;

    public WeatherController(WeatherServiceImpl weatherServiceImpl) {
        this.weatherServiceImpl = weatherServiceImpl;
    }

    @PostMapping(value = "/weather")
    public ResponseEntity<List<WeatherCurrent>> getWeather(@RequestBody WeatherCurrentRequestDto request) throws ParseException {
        request.getCityList().stream().forEach(System.out::println);
        return ResponseEntity.ok(weatherServiceImpl.getWeather(request));
    }


    @GetMapping(value = "/weather/sort")
    public List<WeatherEntity> sortDate(){
        return weatherServiceImpl.sortDate();
    }

    @GetMapping(value = "/weather/sortname")
    public List<WeatherEntity> sortName(){
        return weatherServiceImpl.sortName();
    }




}
