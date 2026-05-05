import org.junit.jupiter.api.Test;
import ru.yandex.practicum.collector.dto.hub.ScenarioCondition;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScenarioConditionTest {

    @Test
    void shouldAcceptIntegerValue() {
        ScenarioCondition condition = new ScenarioCondition();

        assertDoesNotThrow(() -> condition.setValue(10));
        assertEquals(10, condition.getValue());
    }

    @Test
    void shouldAcceptBooleanValue() {
        ScenarioCondition condition = new ScenarioCondition();

        assertDoesNotThrow(() -> condition.setValue(true));
        assertEquals(true, condition.getValue());
    }

    @Test
    void shouldAcceptNullValue() {
        ScenarioCondition condition = new ScenarioCondition();

        assertDoesNotThrow(() -> condition.setValue(null));
        assertEquals(null, condition.getValue());
    }

    @Test
    void shouldRejectUnsupportedValueType() {
        ScenarioCondition condition = new ScenarioCondition();

        assertThrows(IllegalArgumentException.class, () -> condition.setValue("Неверный тип"));
    }
}