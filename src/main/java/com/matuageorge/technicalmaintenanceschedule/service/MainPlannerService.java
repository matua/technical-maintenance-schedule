package com.matuageorge.technicalmaintenanceschedule.service;

import com.google.maps.errors.ApiException;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;

import java.io.IOException;

public interface MainPlannerService {
    void updateSchedule() throws NotFoundException, ValidationException, ResourceAlreadyExistsException, IOException, InterruptedException, ApiException;

    void rescheduleCompletedRegularSchedules();
}
