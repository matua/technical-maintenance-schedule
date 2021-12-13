package ug.payway.technicalmaintenanceschedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@EnableJpaAuditing
@EnableScheduling
public class TechnicalMaintenanceScheduleApplication {

  public static void main(String... args) {
    SpringApplication.run(TechnicalMaintenanceScheduleApplication.class);
  }
}
