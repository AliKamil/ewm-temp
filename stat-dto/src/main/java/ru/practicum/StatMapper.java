package ru.practicum;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.modelDto.EndpointHitInDto;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface StatMapper {
    Object toEndpointHit(EndpointHitInDto endHit);
}
