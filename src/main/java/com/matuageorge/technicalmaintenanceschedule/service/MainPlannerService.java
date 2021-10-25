package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;

public interface MainPlannerService {
    void updateSchedule() throws NotFoundException, ValidationException, ResourceAlreadyExistsException;

    void rescheduleCompletedRegularSchedules();
}
