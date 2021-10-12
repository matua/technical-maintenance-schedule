package com.matuageorge.technicalmaintenanceschedule;

import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.service.api.PayWayApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class TechnicalMaintenanceScheduleApplication implements CommandLineRunner {

    private final PayWayApiService payWayApiService;

    public static void main(String[] args) {
        SpringApplication.run(TechnicalMaintenanceScheduleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<Terminal> currentListOfTerminals = payWayApiService.getCurrentListOfTerminals();

        currentListOfTerminals
//                .filter(
//                        terminal -> terminal.getType().name().equals("Hardware"))
//                .map(Terminal::getName)
                .forEach(t -> log.info(String.valueOf(t)));
    }
}
