package com.matuageorge.technicalmaintenanceschedule;

import com.google.maps.errors.ApiException;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.service.*;
import com.matuageorge.technicalmaintenanceschedule.service.api.google.GoogleMapsDirectionsService;
import com.matuageorge.technicalmaintenanceschedule.service.api.payway.PayWayApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@EnableJpaAuditing
public class TechnicalMaintenanceScheduleApplication implements CommandLineRunner {

    private final PayWayApiService payWayApiService;
    private final TerminalService terminalService;
    private final TaskService taskService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final MainPlannerService mainPlannerService;
    private final ModelMapper modelMapper;
    private final GoogleMapsDirectionsService googleMapsDirectionsService;

    public static void main(String[] args) {
        SpringApplication.run(TechnicalMaintenanceScheduleApplication.class, args);
    }

    @Override
    public void run(String... args) throws NotFoundException, ValidationException, ResourceAlreadyExistsException, IOException, InterruptedException, ApiException {
//        log.info("Updating the Terminals DB...");
//        terminalService.updateListOfTerminalsInDb(TerminalType.HARDWARE);
//        mainPlannerService.updateSchedule();
//        mainPlannerService.rescheduleDone();
//        scheduleService.addUrgentSchedules();

        final List<Terminal> origin = List.of(terminalService.findByName("TERM-1529").get());
        final List<Terminal> destination = List.of(
                terminalService.findByName("TERM-1529").get());
        final List<Terminal> terminalLocations = List.of(
                terminalService.findByName("TERM-1531").get(),
                terminalService.findByName("TERM-1505").get(),
                terminalService.findByName("TERM-0139").get(),
                terminalService.findByName("TERM-0211").get(),
                terminalService.findByName("TERM-1500").get(),
                terminalService.findByName("TERM-6706").get(),
                terminalService.findByName("TERM-0109").get(),
                terminalService.findByName("TERM-1506").get(),
                terminalService.findByName("TERM-6707").get(),
                terminalService.findByName("TERM-6701").get(),
                terminalService.findByName("TERM-1501").get(),
                terminalService.findByName("TERM-0206").get(),
                terminalService.findByName("TERM-1533").get());


//        final Optional<List<Terminal>> optimalRoute = googleMapsDirectionsService.getOptimalRouteListOfTerminals(
//                origin, destination, terminalLocations);
        final Optional<int[]> optimalOrderOfTerminals = googleMapsDirectionsService.getOptimalIndicesOfOrderOfTerminals(
                origin, destination, terminalLocations);

//        log.info(String.valueOf(optimalRoute));
        if (optimalOrderOfTerminals.isPresent()) {
            final int[] ints = optimalOrderOfTerminals.get();

            StringBuilder result = new StringBuilder();
            Arrays.stream(ints)
                    .mapToObj(String::valueOf)
                    .forEach(s -> result.append(s).append(" "));
            log.info(result.toString().trim());
        }
    }
}
