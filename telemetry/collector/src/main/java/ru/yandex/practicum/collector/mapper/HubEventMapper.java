package ru.yandex.practicum.collector.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.dto.hub.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Component
public class HubEventMapper {

    public HubEventAvro toAvro(HubEvent event) {

        HubEventAvro.Builder builder = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp());

        switch (event.getType()) {

            case DEVICE_ADDED ->
                    builder.setPayload(mapDeviceAdded((DeviceAddedEvent) event));

            case DEVICE_REMOVED ->
                    builder.setPayload(mapDeviceRemoved((DeviceRemovedEvent) event));

            case SCENARIO_ADDED ->
                    builder.setPayload(mapScenarioAdded((ScenarioAddedEvent) event));

            case SCENARIO_REMOVED ->
                    builder.setPayload(mapScenarioRemoved((ScenarioRemovedEvent) event));
        }

        return builder.build();
    }

    private DeviceAddedEventAvro mapDeviceAdded(DeviceAddedEvent event) {
        return DeviceAddedEventAvro.newBuilder()
                .setId(event.getId())
                .setType(DeviceTypeAvro.valueOf(event.getDeviceType().name()))
                .build();
    }

    private DeviceRemovedEventAvro mapDeviceRemoved(DeviceRemovedEvent event) {
        return DeviceRemovedEventAvro.newBuilder()
                .setId(event.getId())
                .build();
    }

    private ScenarioAddedEventAvro mapScenarioAdded(ScenarioAddedEvent event) {
        return ScenarioAddedEventAvro.newBuilder()
                .setName(event.getName())
                .setConditions(
                        event.getConditions().stream()
                                .map(this::mapCondition)
                                .toList()
                )
                .setActions(
                        event.getActions().stream()
                                .map(this::mapAction)
                                .toList()
                )
                .build();
    }

    private ScenarioRemovedEventAvro mapScenarioRemoved(ScenarioRemovedEvent event) {
        return ScenarioRemovedEventAvro.newBuilder()
                .setName(event.getName())
                .build();
    }

    private ScenarioConditionAvro mapCondition(ScenarioCondition c) {
        return ScenarioConditionAvro.newBuilder()
                .setSensorId(c.getSensorId())
                .setType(ConditionTypeAvro.valueOf(c.getType().name()))
                .setOperation(ConditionOperationAvro.valueOf(c.getOperation().name()))
                .setValue(mapValue(c.getValue()))
                .build();
    }

    private DeviceActionAvro mapAction(DeviceAction a) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(a.getSensorId())
                .setType(ActionTypeAvro.valueOf(a.getType().name()))
                .setValue((Integer) a.getValue())
                .build();
    }

    private Object mapValue(Object value) {
        if (value instanceof Integer || value instanceof Boolean || value == null) {
            return value;
        }
        throw new IllegalArgumentException("Unsupported condition value: " + value);
    }
}
