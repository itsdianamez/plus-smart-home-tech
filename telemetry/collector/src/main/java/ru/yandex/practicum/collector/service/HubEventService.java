package ru.yandex.practicum.collector.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.dto.hub.HubEvent;
import ru.yandex.practicum.collector.kafka.HubTelemetryProducer;
import ru.yandex.practicum.collector.mapper.HubEventMapper;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
public class HubEventService {

    private final HubEventMapper mapper;
    private final HubTelemetryProducer producer;

    public HubEventService(HubEventMapper mapper, HubTelemetryProducer producer) {
        this.mapper = mapper;
        this.producer = producer;
    }

    public void handle(HubEvent event) {
        HubEventAvro avro = mapper.toAvro(event);
        producer.send(avro);
    }

}
