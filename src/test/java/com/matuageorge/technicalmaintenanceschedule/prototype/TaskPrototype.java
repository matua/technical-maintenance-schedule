package com.matuageorge.technicalmaintenanceschedule.prototype;

import com.matuageorge.technicalmaintenanceschedule.model.Task;

public class TaskPrototype {
    public static Task aTask() {
        return Task.builder()
                .description("General service A")
                .frequency(30)
                .build();
    }

    public static Task bTask() {
        return Task.builder()
                .description("General service B")
                .frequency(30)
                .build();
    }
}
