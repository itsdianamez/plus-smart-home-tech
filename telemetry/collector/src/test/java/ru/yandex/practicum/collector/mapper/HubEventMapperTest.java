package ru.yandex.practicum.collector.mapper;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.collector.dto.hub.*;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class HubEventMapperTest {

    private final HubEventMapper mapper = new HubEventMapper();

    @Test
    void shouldMapScenarioAddedEventToAvro() {
        ScenarioCondition condition = new ScenarioCondition();
        condition.setSensorId("sensor.motion.7");
        condition.setType(ConditionType.MOTION);
        condition.setOperation(ConditionOperation.EQUALS);
        condition.setValue(true);

        DeviceAction action = new DeviceAction();
        action.setSensorId("sensor.switch.1");
        action.setType(ActionType.ACTIVATE);

        ScenarioAddedEvent event = new ScenarioAddedEvent();
        event.setHubId("hub.12345");
        event.setTimestamp(Instant.parse("2024-08-06T15:11:24.157Z"));
        event.setType(event.getType());
        event.setName("turn on light");
        event.setConditions(List.of(condition));
        event.setActions(List.of(action));

        HubEventAvro mapped = mapper.toAvro(event);

        assertEquals("hub.12345", mapped.getHubId());
        assertEquals(Instant.parse("2024-08-06T15:11:24.157Z"), mapped.getTimestamp());

        ScenarioAddedEventAvro payload = assertInstanceOf(ScenarioAddedEventAvro.class, mapped.getPayload());
        ScenarioConditionAvro conditionAvro = payload.getConditions().get(0);
        DeviceActionAvro actionAvro = payload.getActions().get(0);

        assertEquals("turn on light", payload.getName());
        assertEquals("sensor.motion.7", conditionAvro.getSensorId());
        assertEquals(true, conditionAvro.getValue());
        assertEquals("sensor.switch.1", actionAvro.getSensorId());
    }
}
