package com.example.weather.service;

import com.example.weather.dto.WeatherCurrent;
import com.example.weather.dto.WeatherCurrentRequestDto;
import com.example.weather.entity.WeatherEntity;
import com.example.weather.repos.WeatherRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {


    private final RestTemplate restTemplate;
    private final WeatherRepository repository;

    @Override
    public List<WeatherEntity> findAll() {
        return repository.findAll();
    }

    public WeatherServiceImpl(RestTemplate restTemplate, WeatherRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public List<WeatherCurrent> getWeather(WeatherCurrentRequestDto request) throws ParseException {
        List<WeatherCurrent> weatherCurrentList = new ArrayList<>();
        for (String city:request.getCityList()) {
            String url = "https://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid=742ff9c0378ff14907f9e86cdc39835d";
            WeatherCurrent weatherCurrent = restTemplate.getForObject(url,WeatherCurrent.class);
            convertCelsius(weatherCurrent);
            weatherCurrentList.add(weatherCurrent);
        }
        return weatherCurrentList;
    }

    public WeatherCurrent convertCelsius(WeatherCurrent weatherCurrent) throws ParseException {
        for(int i = 0; i < weatherCurrent.getList().size(); i++){
            weatherCurrent.getList().get(i).getMain().setTemp(weatherCurrent.getList().get(i).getMain().getTemp() - 273);
            WeatherEntity weatherEntity = new WeatherEntity();
            weatherEntity.setCity(weatherCurrent.getCity().getName());
            weatherEntity.setTemp(weatherCurrent.getList().get(i).getMain().getTemp());
            weatherEntity.setHumidity(weatherCurrent.getList().get(i).getMain().getHumidity());
            Date date = stringToDate(weatherCurrent.getList().get(i).getDt_txt());
            weatherEntity.setDate(date);
            repository.saveAndFlush(weatherEntity);
        }
        return weatherCurrent;
    }


    public Date stringToDate(String date) throws ParseException {
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date result = sdt.parse(date);
        return result;
    }

    public List<WeatherEntity> sortDate(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "date"));
    }

    public List<WeatherEntity> sortName(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "city"));
    }


}
