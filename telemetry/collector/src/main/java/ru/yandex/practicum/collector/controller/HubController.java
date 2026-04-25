package ru.yandex.practicum.collector.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.collector.dto.hub.HubEvent;
import ru.yandex.practicum.collector.service.HubEventService;

@RestController
@RequestMapping("/events/hubs")
public class HubController {

    private final HubEventService service;

    public HubController(HubEventService service) {
        this.service = service;
    }

    @PostMapping
    public void collect(@Valid @RequestBody HubEvent event) {
        service.handle(event);
    }

}