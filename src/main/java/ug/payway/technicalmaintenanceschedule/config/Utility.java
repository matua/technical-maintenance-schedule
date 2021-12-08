package ug.payway.technicalmaintenanceschedule.config;

import org.springframework.stereotype.Component;

@Component
public class Utility {
  public static final String ADMIN = "/admin";
  public static final String TASKS = "/tasks";
  public static final String SCHEDULES = "/schedules";
  public static final String TERMINALS = "/terminals";
  public static final String USERS = "/users";
  public static final String USER_LOCATIONS = "/user_locations";

  private Utility() {}
}
