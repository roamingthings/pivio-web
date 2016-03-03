package io.pivio.view.overview;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Component
public class OverviewService {

    public JsonNode getDetails(String serverUrl, String id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestCallErrorHandler());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", APPLICATION_JSON_UTF8_VALUE);
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(serverUrl, GET, new HttpEntity<>("", headers), JsonNode.class);
        return responseEntity.getBody();
    }



}

