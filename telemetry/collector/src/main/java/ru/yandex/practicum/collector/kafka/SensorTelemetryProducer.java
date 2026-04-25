package ru.yandex.practicum.collector.kafka;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.io.ByteArrayOutputStream;

@Component
public class SensorTelemetryProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    private static final String TOPIC = "telemetry.sensors.v1";

    public SensorTelemetryProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(SensorEventAvro event) {
        try {
            byte[] bytes = serialize(event);

            kafkaTemplate.send(
                    TOPIC,
                    event.getHubId(),
                    bytes
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] serialize(SensorEventAvro event) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        BinaryEncoder encoder =
                EncoderFactory.get().binaryEncoder(out, null);

        SpecificDatumWriter<SensorEventAvro> writer =
                new SpecificDatumWriter<>(SensorEventAvro.class);

        writer.write(event, encoder);
        encoder.flush();

        return out.toByteArray();
    }
}
