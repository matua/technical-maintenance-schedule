package com.matuageorge.technicalmaintenanceschedule.service.api;

import com.matuageorge.technicalmaintenanceschedule.dto.TerminalDto;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayWayApiServiceImpl implements PayWayApiService {

    @Value("${payway.api.kioskcash.key}")
    private String paywayKioskCashApiKey;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    @Override
    public List<Terminal> getCurrentListOfTerminals() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("X-Api-Key", "de762450-f0bf-4b74-9801-feaa179c83ad");

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
}