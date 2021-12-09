package ug.payway.technicalmaintenanceschedule;

import com.google.maps.errors.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.Scheduled;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Role;
import ug.payway.technicalmaintenanceschedule.model.Schedule;
import ug.payway.technicalmaintenanceschedule.model.User;
import ug.payway.technicalmaintenanceschedule.service.*;
import ug.payway.technicalmaintenanceschedule.service.api.routing.DirectionsService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@EnableJpaAuditing
public class TechnicalMaintenanceScheduleApplication implements CommandLineRunner {

  private final TerminalService terminalService;
  private final UserService userService;
  private final ScheduleService scheduleService;
  private final UserLocationService userLocationService;
  private final MainPlannerService mainPlannerService;
  private final DirectionsService directionsService;
  private final ModelMapper modelMapper;

  @Value("${payway.location.lat.headoffice}")
  private String headOfficeLatitude;

  @Value("${payway.location.long.headoffice}")
  private String headOfficeLongitude;

  @Scheduled(cron = "${cron.schedule}", zone = "${cron.schedule.timezone}")
  public static void main(String[] args) {
    SpringApplication.run(TechnicalMaintenanceScheduleApplication.class, args);
  }

  @Override
  public void run(String... args)
      throws NotFoundException, ValidationException, ResourceAlreadyExistsException, IOException,
          InterruptedException, ApiException {
    //    log.info("Updating the Terminals DB...");
    //    terminalService.updateListOfTerminalsInDb(TerminalType.HARDWARE);
    mainPlannerService.createNewSchedulesForCommonTasksDueAgain();
    //    mainPlannerService.addNewCommonTaskSchedulesIfExist();
    //    mainPlannerService.addUrgentSchedules();

    final List<User> users =
        userService.findAllByRoleAndActiveAndOnDuty(Role.TECHNICIAN, true, true);

    final Page<Schedule> schedulesToOptimize =
        scheduleService
            .findAllSortedByTaskPriorityAndEndExecutionDateTimeNullAndGrabbedExecutionDateTimeNotNull(
                0, users.size() * 10);

    //     distribute urgent tasks
    final Optional<List<Schedule>> optimalIndicesOfOrderOfSchedulesToOptimize =
        directionsService.getOptimalIndicesOfOrderOfSchedules(
            schedulesToOptimize.getContent(),
            users,
            Double.parseDouble(headOfficeLatitude),
            Double.parseDouble(headOfficeLongitude),
            Double.parseDouble(headOfficeLatitude),
            Double.parseDouble(headOfficeLongitude));

    log.info("dummy");
  }
}
