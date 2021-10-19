package com.matuageorge.technicalmaintenanceschedule.service.api;

import com.matuageorge.technicalmaintenanceschedule.model.KioskMessage;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;

import java.util.List;
import java.util.Optional;

public interface PayWayApiService {
    List<Terminal> getCurrentListOfTerminals();

    Optional<List<KioskMessage>> getAllTerminalsToBeUrgentlyServiced(String startTimeStamp, String endTimeStamp);

}
