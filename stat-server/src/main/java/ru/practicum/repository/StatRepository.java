package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.EndpointHit;

import java.util.List;

public interface StatRepository extends JpaRepository<EndpointHit,Long> {
    List<EndpointHit> findByUri(String uri);
}
