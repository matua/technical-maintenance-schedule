// package ug.payway.technicalmaintenanceschedule.service;
//
// import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.stereotype.Service;
// import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
// import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
// import ug.payway.technicalmaintenanceschedule.model.Schedule;
// import ug.payway.technicalmaintenanceschedule.model.Task;
// import ug.payway.technicalmaintenanceschedule.model.TaskStatus;
// import ug.payway.technicalmaintenanceschedule.model.Terminal;
//
// import java.util.List;
//
// @Service
// @Slf4j
// @AllArgsConstructor
// public class UtilityService {
//    private final TerminalService terminalService;
//    private final ScheduleService scheduleService;
//    public void addNewCommonTaskSchedules(Task task) {
//
//        List<Terminal> terminals = terminalService.findAll();
//
//        terminals.forEach(
//                terminal ->
//                {
//                    Schedule schedule = Schedule.builder()
//                            .status(TaskStatus.SCHEDULED)
//                            .terminal(terminal)
//                            .task(task)
//                            .build();
//                    try {
//                        scheduleService.save(schedule);
//                    } catch (ValidationException e) {
//                        e.printStackTrace();
//                    } catch (ResourceAlreadyExistsException e) {
//                        log.error(e.getLocalizedMessage());
//                    }
//                });
//    }
// }
