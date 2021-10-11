package com.matuageorge.technicalmaintenanceschedule.service.api;

import com.matuageorge.technicalmaintenanceschedule.dto.KioskCash;
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
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.add("X-Api-Key", paywayKioskCashApiKey);

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("admin.payway.ug")
                .pathSegment("integration", "query", "recon", "kioskCash.json")
                .build();

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);

        log.info("Trying to get the list of kiosks via PayWay API...");

        ResponseEntity<List<KioskCash>> responseEntity = restTemplate
                .exchange(uri.toUriString(), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
                });

        return modelMapper.map(responseEntity.getBody(), new TypeToken<List<Terminal>>() {
        }.getType());
    }
}
