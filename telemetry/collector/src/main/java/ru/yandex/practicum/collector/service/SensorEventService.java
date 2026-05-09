package ru.yandex.practicum.collector.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.dto.sensor.SensorEvent;
import ru.yandex.practicum.collector.kafka.SensorTelemetryProducer;
import ru.yandex.practicum.collector.mapper.SensorEventMapper;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
public class SensorEventService {

    private final SensorEventMapper mapper;
    private final SensorTelemetryProducer producer;

    public SensorEventService(SensorEventMapper mapper, SensorTelemetryProducer producer) {
        this.mapper = mapper;
        this.producer = producer;
    }

    public void handle(SensorEvent event) {
        SensorEventAvro avro = mapper.toAvro(event);
        producer.send(avro);
    }

}