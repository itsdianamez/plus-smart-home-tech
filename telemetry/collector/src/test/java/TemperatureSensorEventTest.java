import org.junit.jupiter.api.Test;
import ru.yandex.practicum.collector.dto.sensor.TemperatureSensorEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemperatureSensorEventTest {

    @Test
    void shouldSetTemperatureC() {
        TemperatureSensorEvent event = new TemperatureSensorEvent();

        event.setTemperatureC(23);

        assertEquals(23, event.getTemperatureC());
    }

    @Test
    void shouldSetTemperatureF() {
        TemperatureSensorEvent event = new TemperatureSensorEvent();

        event.setTemperatureF(73);

        assertEquals(73, event.getTemperatureF());
    }
}