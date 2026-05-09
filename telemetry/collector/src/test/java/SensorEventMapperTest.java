import org.junit.jupiter.api.Test;
import ru.yandex.practicum.collector.dto.sensor.LightSensorEvent;
import ru.yandex.practicum.collector.dto.sensor.TemperatureSensorEvent;
import ru.yandex.practicum.collector.mapper.SensorEventMapper;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class SensorEventMapperTest {

    private final SensorEventMapper mapper = new SensorEventMapper();

    @Test
    void shouldMapLightSensorEventToAvro() {
        LightSensorEvent event = new LightSensorEvent();
        event.setId("sensor.light.3");
        event.setHubId("hub-2");
        event.setTimestamp(Instant.parse("2024-08-06T16:54:03.129Z"));
        event.setType(event.getType());
        event.setLinkQuality(75);
        event.setLuminosity(59);

        SensorEventAvro mapped = mapper.toAvro(event);

        assertEquals("sensor.light.3", mapped.getId());
        assertEquals("hub-2", mapped.getHubId());
        assertEquals(Instant.parse("2024-08-06T16:54:03.129Z"), mapped.getTimestamp());
        LightSensorAvro payload = assertInstanceOf(LightSensorAvro.class, mapped.getPayload());
        assertEquals(75, payload.getLinkQuality());
        assertEquals(59, payload.getLuminosity());
    }

    @Test
    void shouldMapTemperatureSensorEventToAvro() {
        TemperatureSensorEvent event = new TemperatureSensorEvent();
        event.setId("sensor.temp.1");
        event.setHubId("hub-1");
        event.setTimestamp(Instant.parse("2024-08-06T17:54:03.129Z"));
        event.setType(event.getType());
        event.setTemperatureC(23);
        event.setTemperatureF(73);

        SensorEventAvro mapped = mapper.toAvro(event);

        TemperatureSensorAvro payload = assertInstanceOf(TemperatureSensorAvro.class, mapped.getPayload());
        assertEquals(23, payload.getTemperatureC());
        assertEquals(73, payload.getTemperatureF());
    }

}