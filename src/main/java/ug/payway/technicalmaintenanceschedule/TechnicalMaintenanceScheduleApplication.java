package ug.payway.technicalmaintenanceschedule;

import com.google.maps.errors.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@EnableJpaAuditing
public class TechnicalMaintenanceScheduleApplication implements CommandLineRunner {

  private final TerminalService terminalService;
  private final UserService userService;
  private final ScheduleService scheduleService;
  private final MainPlannerService mainPlannerService;
  private final DirectionsService directionsService;

  @Value("${payway.location.lat.headoffice}")
  private String headOfficeLatitude;

  @Value("${payway.location.long.headoffice}")
  private String headOfficeLongitude;

  public static void main(String... args) {
    SpringApplication.run(TechnicalMaintenanceScheduleApplication.class);
  }

  @Override
  public void run(String... args)
      throws NotFoundException, ValidationException, ResourceAlreadyExistsException, IOException,
          InterruptedException, ApiException {
    log.info("Updating the Terminals DB...");
    terminalService.updateListOfTerminalsInDb(TerminalType.HARDWARE);
    mainPlannerService.createNewSchedulesForCommonTasksDueAgain();
    mainPlannerService.addNewCommonTaskSchedulesIfExist();
    mainPlannerService.addUrgentSchedules();

    final List<User> users =
        userService.findAllByRoleAndActiveAndOnDuty(Role.TECHNICIAN, true, true);

    // find schedules' amount assigned but not completed
    final List<Schedule> schedulesAssignedButNotCompleted =
        scheduleService.findAllByEndExecutionDateTimeNullAndUserIdNotNull();
    final int numberOfSchedulesAssignedBytNotCompleted = schedulesAssignedButNotCompleted.size();

    final int pageSize = users.size() * 15 - numberOfSchedulesAssignedBytNotCompleted;

    Page<Schedule> schedulesToOptimize = Page.empty();
    if (pageSize != 0) {
      schedulesToOptimize =
          scheduleService
              .findAllSortedByTaskPriorityAndEndExecutionDateTimeNullAndGrabbedExecutionDateTimeNotNull(
                  0, pageSize);
    }

    schedulesAssignedButNotCompleted.addAll(schedulesToOptimize.getContent());

    // find urgent schedules, and add them in case if they are not added because of the allowed size
    // to be distributed
    // (45)
    List<Schedule> urgentSchedulesOnly =
        scheduleService.findAllByTaskPriorityAndEndExecutionDateTimeNull(TaskPriority.URGENT);
    schedulesAssignedButNotCompleted.addAll(urgentSchedulesOnly);

    //     distribute urgent tasks
    directionsService.getOptimalIndicesOfOrderOfSchedules(
        Set.copyOf(schedulesAssignedButNotCompleted).stream().toList(),
        users,
        Double.parseDouble(headOfficeLatitude),
        Double.parseDouble(headOfficeLongitude),
        Double.parseDouble(headOfficeLatitude),
        Double.parseDouble(headOfficeLongitude));

    log.info("DONE!");
  }
}
