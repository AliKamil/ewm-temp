package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitInDto;
import ru.practicum.dto.EndpointHitOutDto;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class StatServerController {
    private StatService service;

    @PostMapping("/hit")
    public ResponseEntity<String> addHit(@RequestBody EndpointHitInDto endpointHitInDto) {
        service.addHit(endpointHitInDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Информация сохранена");

    }

    @GetMapping("/stats")
    public List<EndpointHitOutDto> getStats(@RequestParam(name = "start") String start,
                                            @RequestParam(name = "end") String end,
                                            @RequestParam(name = "uris") List<String> uris,
                                            @RequestParam(name = "unique",required = false,defaultValue ="false") Boolean unique) {
        return service.getStat(start,end,uris,unique);
    }
}
