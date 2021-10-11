package com.matuageorge.technicalmaintenanceschedule.service.api;

import com.matuageorge.technicalmaintenanceschedule.model.Terminal;

import java.util.List;

public interface PayWayApiService {
    List<Terminal> getCurrentListOfTerminals();
}
