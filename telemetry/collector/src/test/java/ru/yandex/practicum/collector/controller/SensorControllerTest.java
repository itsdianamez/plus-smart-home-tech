package ru.yandex.practicum.collector.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import ru.yandex.practicum.collector.dto.sensor.SensorEvent;
import ru.yandex.practicum.collector.service.SensorEventService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SensorControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        SensorEventService service = new SensorEventService(null, null) {
            @Override
            public void handle(SensorEvent event) {
                // no-op
            }
        };

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new SensorController(service))
                .setValidator(validator)
                .build();
    }

    @Test
    void shouldAcceptValidSensorEvent() throws Exception {
        String body = """
                {
                  "id": "sensor.light.3",
                  "hubId": "hub-2",
                  "timestamp": "2024-08-06T16:54:03.129Z",
                  "type": "LIGHT_SENSOR_EVENT",
                  "linkQuality": 75,
                  "luminosity": 59
                }
                """;

        mockMvc.perform(post("/events/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectInvalidSensorEvent() throws Exception {
        String body = """
                {
                  "id": "sensor.light.3",
                  "hubId": "hub-2",
                  "timestamp": "2024-08-06T16:54:03.129Z",
                  "type": "LIGHT_SENSOR_EVENT",
                  "luminosity": 59
                }
                """;

        mockMvc.perform(post("/events/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}
