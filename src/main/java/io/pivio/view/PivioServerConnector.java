package io.pivio.view;

import io.pivio.view.configuration.ServerConfig;
import io.pivio.view.overview.model.OverviewCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Component
public class PivioServerConnector {

    private final Logger log = LoggerFactory.getLogger(PivioServerConnector.class);

    @Autowired
    ServerConfig serverConfig;

    public <T> ResponseEntity query(String path, String query, Class<T> type) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        String url = serverConfig.apiAddress + path + encodedQuery;
        log.debug("Asking: " + url);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers), type);
        log.debug(response.getBody().toString());
        return response;
    }

    public ResponseEntity getOverviewCards() throws UnsupportedEncodingException {
        String path = "/document?fields=short_name,id,description,name,owner,context,lastUpdate,lastUpload&sort=name:asc";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        String url = serverConfig.apiAddress + path;
        log.debug("Asking: " + url);

        ParameterizedTypeReference<List<OverviewCard>> typeRef = new ParameterizedTypeReference<List<OverviewCard>>() {
        };

        ResponseEntity<List<OverviewCard>> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers), typeRef);
        log.debug(response.getBody().toString());
        return response;
    }




}
