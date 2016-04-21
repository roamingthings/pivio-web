package io.pivio.view.overview;

import io.pivio.view.PivioServerConnector;
import io.pivio.view.configuration.ServerConfig;
import io.pivio.view.overview.model.OverviewCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

@Component
public class OverviewService {

    @Autowired
    ServerConfig serverConfig;

    @Autowired
    PivioServerConnector pivioServerConnector;

    private final Logger log = LoggerFactory.getLogger(OverviewService.class);

    List<OverviewCard> getOverview() throws IOException {
        String path = "/document?fields=short_name,id,description,name,owner,context,lastUpdate,lastUpload&sort=name:asc";
        String url = serverConfig.apiAddress + path;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        log.debug("Asking: " + url);
        ParameterizedTypeReference<List<OverviewCard>> typeRef = new ParameterizedTypeReference<List<OverviewCard>>() {
        };
        List<OverviewCard> result = new ArrayList<>();
        try {
            ResponseEntity<List<OverviewCard>> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers), typeRef);
            log.debug(response.getBody().toString());
            result = response.getBody();
            sort(result);
        } catch (Exception e) {
            throw new IOException();
        }
        return result;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    boolean deleteDocument(String id) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        String url = serverConfig.apiAddress + "/document/" + id;
        ResponseEntity<Object> exchange = null;
        try {
            exchange = restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>("", headers), Object.class);
        } catch (Exception e) {
            throw new IOException();
        }
        return exchange != null && exchange.getStatusCode() == HttpStatus.ACCEPTED;
    }
}
