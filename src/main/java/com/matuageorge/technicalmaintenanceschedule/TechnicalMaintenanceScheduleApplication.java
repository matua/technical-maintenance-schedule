package com.matuageorge.technicalmaintenanceschedule;

import com.google.maps.errors.ApiException;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.TerminalType;
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
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;

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
        log.info("Updating the Terminals DB...");
        terminalService.updateListOfTerminalsInDb(TerminalType.HARDWARE);
//        mainPlannerService.addNewCommonTaskSchedulesIfExist();
//        mainPlannerService.createNewSchedulesForCommonTasksDueAgain();
//        mainPlannerService.addUrgentSchedules();
//
//        final Page<Schedule> all = scheduleService.findAll(0, 40);
//        final List<User> users = userService.findAllByRoleAndActiveAndOnDuty(Role.TECHNICIAN, true, true);
//
//        directionsService.getOptimalIndicesOfOrderOfSchedules(all.getContent(), users);
    }
}
