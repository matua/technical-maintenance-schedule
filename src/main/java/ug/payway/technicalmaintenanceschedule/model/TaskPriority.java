package ug.payway.technicalmaintenanceschedule.model;

import lombok.Getter;

@Getter
public enum TaskPriority {
  COMMON("Common"),
  MEDIUM("Medium"),
  URGENT("Urgent");
  private final String name;

  TaskPriority(String name) {
    this.name = name;
  }
}
