package ug.payway.technicalmaintenanceschedule.service.api.payway;

import ug.payway.technicalmaintenanceschedule.model.KioskMessage;
import ug.payway.technicalmaintenanceschedule.model.Terminal;

import java.util.List;
import java.util.Optional;

public interface PayWayApiService {
    List<Terminal> getCurrentListOfTerminals();

    Optional<List<KioskMessage>> getAllTerminalsToBeUrgentlyServiced(String startTimeStamp, String endTimeStamp);
}