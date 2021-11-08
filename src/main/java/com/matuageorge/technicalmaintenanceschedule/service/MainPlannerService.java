package com.matuageorge.technicalmaintenanceschedule.service;

import com.google.maps.errors.ApiException;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Schedule;

import java.io.IOException;
import java.util.List;

public interface MainPlannerService {
    void addNewCommonTaskSchedulesIfExist() throws NotFoundException, ValidationException, ResourceAlreadyExistsException, IOException, InterruptedException, ApiException;

    void createNewSchedulesForCommonTasksDueAgain();

    List<Schedule> addUrgentSchedules() throws ValidationException, NotFoundException;

    void allocateSchedulesBetweenUsers(int numberOfSchedules, int numberOfUsers);
}
