package com.matuageorge.technicalmaintenanceschedule.prototype;

import com.matuageorge.technicalmaintenanceschedule.dto.TaskDto;

public class TaskDtoPrototype {
    public static TaskDto aTaskDto() {
        return TaskDto.builder()
                .description("General service B")
                .frequency(30)
                .build();
    }

    public static TaskDto bTaskDto() {
        return TaskDto.builder()
                .description("General service B")
                .frequency(30)
                .build();
    }
}
