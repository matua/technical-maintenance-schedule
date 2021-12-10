package ug.payway.technicalmaintenanceschedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import ug.payway.technicalmaintenanceschedule.model.KioskMessage;

class TestDeserialization {

    private static final ObjectMapper om = new ObjectMapper();

    @Test
    void jsonToMap() throws JsonProcessingException {
        val json = """
                [
                    {
                        "args": "{ \\"Operator\\" : \\"URA\\", \\"Subscriber\\" : \\"0787355587\\", \\"Reason\\" : \\"Service provider error\\" }",
                        "arrived": "2021-10-10 21:51:11",
                        "id": 10344846755,
                        "kiosk": "TERM-1504",
                        "name": "VALIDATION_ERROR",
                        "occured": "2021-10-10 21:51:08"
                    },
                    {
                        "args": "{ \\"Code\\" : \\"BillValidator.Full\\", \\"Since\\" : \\"10/10/2021 20:42:14\\", \\"Duration\\" : \\"4239\\" }",
                        "arrived": "2021-10-10 21:53:03",
                        "id": 10344846756,
                        "kiosk": "TERM-1501",
                        "name": "OUT_OF_SERVICE",
                        "occured": "2021-10-10 21:52:54"
                    }]
                """;

        val r = om.readValue(json, KioskMessage.class);
    }
}