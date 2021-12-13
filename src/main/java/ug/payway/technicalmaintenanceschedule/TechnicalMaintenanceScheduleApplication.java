package ug.payway.technicalmaintenanceschedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@EnableJpaAuditing
public class TechnicalMaintenanceScheduleApplication {

  public static void main(String... args) {
    SpringApplication.run(TechnicalMaintenanceScheduleApplication.class);
  }
}
