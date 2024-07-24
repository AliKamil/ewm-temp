package ru.practicum.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface StatMapper {
    Object toEndpointHit(EndpointHitInDto endHit);
}
