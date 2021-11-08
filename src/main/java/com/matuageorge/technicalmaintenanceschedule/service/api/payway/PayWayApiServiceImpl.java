package com.matuageorge.technicalmaintenanceschedule.service.api.payway;

import com.matuageorge.technicalmaintenanceschedule.dto.TerminalDto;
import com.matuageorge.technicalmaintenanceschedule.model.KioskMessage;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayWayApiServiceImpl implements PayWayApiService {

    @Value("${payway.api.kioskcash.key}")
    private String paywayKioskCashApiKey;
    @Value("${payway.api.messages.key}")
    private String paywayMessagesApiKey;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    @Override
    public List<Terminal> getCurrentListOfTerminals() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("X-Api-Key", paywayKioskCashApiKey);

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("admin.payway.ug")
                .pathSegment("integration", "query", "technical-maintenance-schedule", "terminalList.json")
                .build();

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(headers);

        log.info("Trying to get the list of kiosks via PayWay API...");

        ResponseEntity<List<TerminalDto>> responseEntity = restTemplate
                .exchange(uri.toUriString(), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
                });

        return modelMapper.map(responseEntity.getBody(), new TypeToken<List<Terminal>>() {
        }.getType());
    }

    @Override
    public Optional<List<KioskMessage>> getAllTerminalsToBeUrgentlyServiced(String startTimeStamp,
                                                                            String endTimeStamp) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.add("X-Api-Key", paywayMessagesApiKey);

        UriComponents build = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("admin.payway.ug")
                .pathSegment("integration", "query", "kiosks", "kioskMessages.json")
                .queryParam(
                        "From", startTimeStamp)
                .queryParam("To", endTimeStamp)
                .build();

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(headers);

        log.info("Trying to get the list of OUT_OF_SERVICE messages from {} to {} from kiosks...",
                startTimeStamp, endTimeStamp);

        ResponseEntity<List<KioskMessage>> responseEntity = restTemplate
                .exchange(build.toUriString(),
                        HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
                        });

        List<KioskMessage> listOfKiosksOutOfService = getKiosksWithOutOfServiceMessage(responseEntity);

        log.info("Quantity of kiosks to be urgently serviced: {}", listOfKiosksOutOfService.size());
        log.info("Kiosks to be urgently serviced:");
        listOfKiosksOutOfService.forEach(kioskMessage -> log.info(kioskMessage.getKiosk()));

        return Optional.of(listOfKiosksOutOfService);
    }

    private List<KioskMessage> getKiosksWithOutOfServiceMessage(ResponseEntity<List<KioskMessage>> responseEntity) {

        log.info("Getting kiosks and error messages with OUT_OF_SERVICE status");

        return Objects.requireNonNull(responseEntity.getBody())
                .stream().filter(kioskMessage -> kioskMessage.getName().equalsIgnoreCase("OUT_OF_SERVICE"))
                .distinct()
                .toList();
    }
}
