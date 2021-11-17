package ug.payway.technicalmaintenanceschedule.service;

import com.google.maps.errors.ApiException;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Schedule;

import java.io.IOException;
import java.util.List;

public interface MainPlannerService {
    void addNewCommonTaskSchedulesIfExist() throws NotFoundException, ValidationException, ResourceAlreadyExistsException, IOException, InterruptedException, ApiException;

    void createNewSchedulesForCommonTasksDueAgain();

    List<Schedule> addUrgentSchedules() throws ValidationException, NotFoundException;
}
