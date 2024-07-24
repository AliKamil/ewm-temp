package ru.practicum.service;

import ru.practicum.modelDto.EndpointHitInDto;
import ru.practicum.modelDto.EndpointHitOutDto;

import java.util.List;

public interface StatService {
    void addHit(EndpointHitInDto endpointHitInDto);


    List<EndpointHitOutDto> getStat(String start, String end, List<String> uri, Boolean unq);
}
