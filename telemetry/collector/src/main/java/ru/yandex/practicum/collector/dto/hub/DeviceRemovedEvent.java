package ru.yandex.practicum.collector.dto.hub;

import jakarta.validation.constraints.NotBlank;

public class DeviceRemovedEvent extends HubEvent {

    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }

}