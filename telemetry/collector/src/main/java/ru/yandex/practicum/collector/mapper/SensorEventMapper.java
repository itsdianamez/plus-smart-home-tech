package ru.yandex.practicum.collector.mapper;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.dto.sensor.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Component
public class SensorEventMapper {

    public SensorEventAvro toAvro(SensorEvent event) {

        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(mapPayload(event))
                .build();
    }

    private SpecificRecord mapPayload(SensorEvent event) {

        return switch (event.getType()) {

            case LIGHT_SENSOR_EVENT -> mapLight((LightSensorEvent) event);

            case MOTION_SENSOR_EVENT -> mapMotion((MotionSensorEvent) event);

            case SWITCH_SENSOR_EVENT -> mapSwitch((SwitchSensorEvent) event);

            case TEMPERATURE_SENSOR_EVENT -> mapTemperature((TemperatureSensorEvent) event);

            case CLIMATE_SENSOR_EVENT -> mapClimate((ClimateSensorEvent) event);
        };
    }

    private LightSensorAvro mapLight(LightSensorEvent e) {
        return LightSensorAvro.newBuilder()
                .setLinkQuality(e.getLinkQuality())
                .setLuminosity(e.getLuminosity())
                .build();
    }

    private MotionSensorAvro mapMotion(MotionSensorEvent e) {
        return MotionSensorAvro.newBuilder()
                .setLinkQuality(e.getLinkQuality())
                .setMotion(e.getMotion())
                .setVoltage(e.getVoltage())
                .build();
    }

    private SwitchSensorAvro mapSwitch(SwitchSensorEvent e) {
        return SwitchSensorAvro.newBuilder()
                .setState(e.getState())
                .build();
    }

    private TemperatureSensorAvro mapTemperature(TemperatureSensorEvent e) {
        return TemperatureSensorAvro.newBuilder()
                .setTemperatureC(e.getTemperatureC())
                .setTemperatureF(e.getTemperatureF())
                .build();
    }

    private ClimateSensorAvro mapClimate(ClimateSensorEvent e) {
        return ClimateSensorAvro.newBuilder()
                .setTemperatureC(e.getTemperatureC())
                .setHumidity(e.getHumidity())
                .setCo2Level(e.getCo2Level())
                .build();
    }

}