package com.matuageorge.technicalmaintenanceschedule.service.api;

import com.matuageorge.technicalmaintenanceschedule.model.Terminal;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PayWayApiService {
    List<Terminal> getCurrentListOfTerminals();

    Optional<Map<String, String>> getAllTerminalsToBeUrgentlyServiced(String startTimeStamp, String endTimeStamp);

}
