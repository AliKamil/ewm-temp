package ru.practicum.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndpointHitInDto {
    private String app;
    private String uri;
    private String ip;
    private String dateHit;
}
