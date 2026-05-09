package ru.yandex.practicum.collector.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.collector.dto.sensor.SensorEvent;
import ru.yandex.practicum.collector.service.SensorEventService;

@RestController
@RequestMapping("/events/sensors")
public class SensorController {

    private final SensorEventService service;

    public SensorController(SensorEventService service) {
        this.service = service;
    }

    @PostMapping
    public void collect(@Valid @RequestBody SensorEvent event) {
        service.handle(event);
    }

}