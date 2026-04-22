package ru.yandex.practicum.collector.dto.sensor;

import jakarta.validation.constraints.NotNull;

public class LightSensorEvent extends SensorEvent {

    @NotNull
    private Integer linkQuality;
    @NotNull
    private Integer luminosity;

    public Integer getLinkQuality() {
        return linkQuality;
    }

    public void setLinkQuality(Integer linkQuality) {
        this.linkQuality = linkQuality;
    }

    public Integer getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(Integer luminosity) {
        this.luminosity = luminosity;
    }

    @Override
    public SensorEventType getType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }

}