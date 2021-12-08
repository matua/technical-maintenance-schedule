package ug.payway.technicalmaintenanceschedule.prototype;

import ug.payway.technicalmaintenanceschedule.model.Task;

public class TaskPrototype {
  public static Task aTask() {
    return Task.builder().description("General service A").frequency(30).build();
  }

  public static Task bTask() {
    return Task.builder().description("General service B").frequency(30).build();
  }
}
