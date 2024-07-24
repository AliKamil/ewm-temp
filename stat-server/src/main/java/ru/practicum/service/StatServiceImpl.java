package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.StatMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.modelDto.EndpointHitInDto;
import ru.practicum.modelDto.EndpointHitOutDto;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private StatRepository statRepository;
    private StatMapper statMapper;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void addHit(EndpointHitInDto endpointHitInDto) {
        EndpointHit endpointHit = (EndpointHit) statMapper.toEndpointHit(endpointHitInDto);
        statRepository.save(endpointHit);
    }

    @Override
    public List<EndpointHitOutDto> getStat(String start, String end, List<String> uri, Boolean unq) {

        LocalDateTime startFormat = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endFormat = LocalDateTime.parse(end, FORMATTER);
        List<EndpointHit> endpointHits = uri.stream()
                .map(url -> statRepository.findByUri(url))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (endpointHits == null || endpointHits.isEmpty()) {
            return new ArrayList<>();
        }

        List<EndpointHitOutDto> result = endpointHits.stream()
                .filter(hit -> hit.getDateHit().isAfter(startFormat) || hit.getDateHit().isBefore(endFormat))
                .collect(Collectors.groupingBy(
                        hit -> Arrays.asList(hit.getApp(), hit.getUri()),
                        Collectors.mapping(
                                EndpointHit::getIp,
                                Collectors.counting()
                        )
                ))
                .entrySet().stream()
                .map(entry -> {
                    List<String> key = entry.getKey();
                    String app = key.get(0);
                    String uriKey = key.get(1);
                    return new EndpointHitOutDto(
                            app,
                            uriKey,
                            entry.getValue()
                    );
                })
                .collect(Collectors.toList());

        if (unq) {
            result = endpointHits.stream()
                    .filter(hit -> hit.getDateHit().isAfter(startFormat) || hit.getDateHit().isBefore(endFormat))
                    .collect(Collectors.groupingBy(
                            hit -> Arrays.asList(hit.getApp(), hit.getUri()),
                            Collectors.mapping(
                                    EndpointHit::getIp,
                                    Collectors.toSet()
                            )
                    ))
                    .entrySet().stream()
                    .map(entry -> {
                        List<String> key = entry.getKey();
                        String app = key.get(0);
                        String uriKey = key.get(1);
                        return new EndpointHitOutDto(
                                app,
                                uriKey,
                                (long) entry.getValue().size()
                        );
                    })
                    .collect(Collectors.toList());
        }
        return result;
    }
}
