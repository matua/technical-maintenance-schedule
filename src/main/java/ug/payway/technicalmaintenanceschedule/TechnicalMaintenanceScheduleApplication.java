package ug.payway.technicalmaintenanceschedule;

import com.google.maps.errors.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.*;
import ug.payway.technicalmaintenanceschedule.service.MainPlannerService;
import ug.payway.technicalmaintenanceschedule.service.ScheduleService;
import ug.payway.technicalmaintenanceschedule.service.TerminalService;
import ug.payway.technicalmaintenanceschedule.service.UserService;
import ug.payway.technicalmaintenanceschedule.service.api.routing.DirectionsService;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@EnableJpaAuditing
public class TechnicalMaintenanceScheduleApplication implements CommandLineRunner {

    private final TerminalService terminalService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final MainPlannerService mainPlannerService;
    private final ModelMapper modelMapper;
    @Qualifier("myrouteonline")
    private final DirectionsService directionsService;

    public static void main(String[] args) {
        SpringApplication.run(TechnicalMaintenanceScheduleApplication.class, args);
    }

    @Override
    public void run(String... args) throws NotFoundException, ValidationException, ResourceAlreadyExistsException, IOException, InterruptedException, ApiException {
        log.info("Updating the Terminals DB...");
        terminalService.updateListOfTerminalsInDb(TerminalType.HARDWARE);
        mainPlannerService.addNewCommonTaskSchedulesIfExist();
        mainPlannerService.createNewSchedulesForCommonTasksDueAgain();
        mainPlannerService.addUrgentSchedules();

        final List<User> users = userService.findAllByRoleAndActiveAndOnDuty(Role.TECHNICIAN, true, true);

        List<Schedule> urgentSchedules =
                scheduleService.findAllByTaskPriorityAndEndExecutionDateTimeNull(TaskPriority.URGENT);
        Page<Schedule> commonSchedules =
                scheduleService.findAllByTaskPriorityAndEndExecutionDateTimeNull(TaskPriority.COMMON, 0,
                        users.size() * 10);

        //distribute urgent tasks
        directionsService.getOptimalIndicesOfOrderOfSchedules(urgentSchedules, users);
        //distribute common tasks
        directionsService.getOptimalIndicesOfOrderOfSchedules(commonSchedules.stream().toList(), users);

        log.info("dummy");

    }
}