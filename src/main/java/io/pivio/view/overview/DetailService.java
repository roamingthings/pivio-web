package io.pivio.view.overview;

import io.pivio.view.PivioServerConnector;
import io.pivio.view.configuration.ServerConfig;
import io.pivio.view.overview.model.Connection;
import io.pivio.view.overview.model.PivioDetail;
import io.pivio.view.overview.model.Service;
import io.pivio.view.overview.model.UsedBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@Component
class DetailService {

    @Autowired
    ServerConfig serverConfig;

    @Autowired
    PivioServerConnector pivioServerConnector;

    private final Logger log = LoggerFactory.getLogger(OverviewService.class);

    PivioDetail getDetail(String id) {
        PivioDetail result = new PivioDetail();
        try {
            ResponseEntity pivioDetail = pivioServerConnector.query("document/", id, PivioDetail.class);
            PivioDetail detail = (PivioDetail) pivioDetail.getBody();
            detail.usedBy = getUsage(id, detail.service, getConnections());
            return detail;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<Connection> getConnections() {
        String path = "/document?fields=short_name,id,service";
        String url = serverConfig.apiAddress + path;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        log.debug("Asking: " + url);
        ParameterizedTypeReference<List<Connection>> typeRef = new ParameterizedTypeReference<List<Connection>>() {
        };
        ResponseEntity<List<Connection>> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers), typeRef);
        log.debug(response.getBody().toString());
        return response.getBody();
    }

    List<UsedBy> getUsage(String serviceId, Service service, List<Connection> connections) {
        List<UsedBy> result = new ArrayList<>();

        List<String> offeredServices = new ArrayList<>();

        for (Service.Provides provide : service.provides) {
            offeredServices.add(serviceId + "_" + provide.port);
            offeredServices.add(provide.service_name);
        }

        for (Connection connection : connections) {
            if (connection.hasDependencies()) {
                for (String dependsOn : connection.service.depends_on.internal) {
                    if (offeredServices.contains(dependsOn)) {
                        result.add(new UsedBy(connection.id, dependsOn));
                    }
                }
            }
        }
        return result;
    }
}
