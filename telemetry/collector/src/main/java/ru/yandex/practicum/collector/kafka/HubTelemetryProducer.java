package ru.yandex.practicum.collector.kafka;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.io.ByteArrayOutputStream;

@Component
public class HubTelemetryProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final String topic;

    public HubTelemetryProducer(
            KafkaTemplate<String, byte[]> kafkaTemplate,
            @Value("${collector.kafka.topics.hubs}") String topic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }
    public void send(HubEventAvro event) {
        try {
            byte[] bytes = serialize(event);

            kafkaTemplate.send(
                    topic,
                    event.getHubId(),
                    bytes
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] serialize(HubEventAvro event) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        BinaryEncoder encoder =
                EncoderFactory.get().binaryEncoder(out, null);

        SpecificDatumWriter<HubEventAvro> writer =
                new SpecificDatumWriter<>(HubEventAvro.class);

        writer.write(event, encoder);
        encoder.flush();

        return out.toByteArray();
    }

}