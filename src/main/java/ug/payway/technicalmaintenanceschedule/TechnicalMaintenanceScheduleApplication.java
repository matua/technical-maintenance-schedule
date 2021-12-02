package ug.payway.technicalmaintenanceschedule;

import com.google.maps.errors.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.TerminalType;
import ug.payway.technicalmaintenanceschedule.service.MainPlannerService;
import ug.payway.technicalmaintenanceschedule.service.ScheduleService;
import ug.payway.technicalmaintenanceschedule.service.TerminalService;
import ug.payway.technicalmaintenanceschedule.service.UserService;
import ug.payway.technicalmaintenanceschedule.service.api.routing.DirectionsService;

import java.io.IOException;

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
    @Qualifier("graphhopper")
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

//        final List<User> users = userService.findAllByRoleAndActiveAndOnDuty(Role.TECHNICIAN, true, true);
//        final Page<ScheduleDto> schedules = scheduleService.findAll(0, users.size() * 20);
//
//        directionsService.getOptimalIndicesOfOrderOfSchedules(
//                schedules.map(scheduleDto -> modelMapper.map(scheduleDto, Schedule.class)).getContent(), users);
    }
}