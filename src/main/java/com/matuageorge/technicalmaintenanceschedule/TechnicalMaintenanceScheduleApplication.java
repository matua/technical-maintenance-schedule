package com.matuageorge.technicalmaintenanceschedule;

import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.TerminalType;
import com.matuageorge.technicalmaintenanceschedule.service.*;
import com.matuageorge.technicalmaintenanceschedule.service.api.PayWayApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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
    private final PlannerService plannerService;
    private final ModelMapper modelMapper;

    public static void main(String[] args) {
        SpringApplication.run(TechnicalMaintenanceScheduleApplication.class, args);
    }

    @Override
    public void run(String... args) throws NotFoundException, ValidationException, ResourceAlreadyExistsException {
        log.info("Updating the Terminals DB...");
        terminalService.updateListOfTerminalsInDb(TerminalType.HARDWARE);
        plannerService.updateSchedule();
        plannerService.rescheduleDone();
        scheduleService.addUrgentSchedules();
    }
}
