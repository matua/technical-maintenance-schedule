package com.matuageorge.technicalmaintenanceschedule;

import com.google.maps.errors.ApiException;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Role;
import com.matuageorge.technicalmaintenanceschedule.model.Schedule;
import com.matuageorge.technicalmaintenanceschedule.model.User;
import com.matuageorge.technicalmaintenanceschedule.service.*;
import com.matuageorge.technicalmaintenanceschedule.service.api.payway.PayWayApiService;
import com.matuageorge.technicalmaintenanceschedule.service.api.routing.DirectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.util.List;

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
    @Qualifier("grasshopper")
    private final DirectionsService directionsService;

    public static void main(String[] args) {
        SpringApplication.run(TechnicalMaintenanceScheduleApplication.class, args);
    }

    @Override
    public void run(String... args) throws NotFoundException, ValidationException, ResourceAlreadyExistsException, IOException, InterruptedException, ApiException {
//        log.info("Updating the Terminals DB...");
//        terminalService.updateListOfTerminalsInDb(TerminalType.HARDWARE);
//        mainPlannerService.addNewCommonTaskSchedulesIfExist();
//        mainPlannerService.createNewSchedulesForCommonTasksDueAgain();
//        mainPlannerService.addUrgentSchedules();
//
        final Page<Schedule> all = scheduleService.findAll(0, 40);
        final List<User> users = userService.findAllByRoleAndActiveAndOnDuty(Role.TECHNICIAN, true, true);

        directionsService.getOptimalIndicesOfOrderOfSchedules(all.getContent(), users);

//        final List<Terminal> origin = List.of(terminalService.findByName("TERM-1529").get());
//        final List<Terminal> destination = List.of(
//                terminalService.findByName("TERM-1529").get());
//        final List<Terminal> terminalLocations = List.of(
//                terminalService.findByName("TERM-1525").get(),
//                terminalService.findByName("TERM-3149").get(),
//                terminalService.findByName("TERM-7829").get(),
//                terminalService.findByName("TERM-1502").get(),
//                terminalService.findByName("TERM-0206").get(),
//                terminalService.findByName("TERM-1533").get(),
//                terminalService.findByName("TERM-0244").get(),
//                terminalService.findByName("TERM-0210").get(),
//                terminalService.findByName("TERM-6706").get(),
//                terminalService.findByName("TERM-6708").get(),
//                terminalService.findByName("TERM-1524").get(),
//                terminalService.findByName("TERM-7833").get(),
//                terminalService.findByName("TERM-1509").get(),
//                terminalService.findByName("TERM-0211").get(),
//                terminalService.findByName("TERM-6704").get(),
//                terminalService.findByName("TERM-7832").get(),
//                terminalService.findByName("TERM-1526").get(),
//                terminalService.findByName("TERM-001").get(),
//                terminalService.findByName("TERM-3151").get(),
//                terminalService.findByName("TERM-7834").get(),
//                terminalService.findByName("TERM-1500").get(),
//                terminalService.findByName("TERM-1531").get(),
//                terminalService.findByName("TERM-005").get(),
//                terminalService.findByName("TERM-6705").get(),
//                terminalService.findByName("TERM-1507").get(),
//                terminalService.findByName("TERM-1503").get(),
//                terminalService.findByName("TERM-0208").get(),
//                terminalService.findByName("TERM-3145").get(),
//                terminalService.findByName("TERM-7835").get(),
//                terminalService.findByName("TERM-0207").get(),
//                terminalService.findByName("TERM-0139").get(),
//                terminalService.findByName("TERM-6702").get());
//
//
//        final Optional<List<Terminal>> optimalRoute = directionsService.getOptimalRouteListOfTerminals(
//                origin, destination, terminalLocations);
//        final Optional<int[]> optimalOrderOfTerminals = googleMapsDirectionsService.getOptimalIndicesOfOrderOfTerminals(
//                origin, destination, terminalLocations);

//        optimalRoute.get().forEach(terminal -> log.info(terminal.getName()));
//        if (optimalOrderOfTerminals.isPresent()) {
//            final int[] ints = optimalOrderOfTerminals.get();
//
//            StringBuilder result = new StringBuilder();
//            Arrays.stream(ints)
//                    .mapToObj(String::valueOf)
//                    .forEach(s -> result.append(s).append(" "));
//            log.info(result.toString().trim());
//        }
    }
}
