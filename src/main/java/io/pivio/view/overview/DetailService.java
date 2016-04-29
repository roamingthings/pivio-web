package io.pivio.view.overview;

import io.pivio.view.PivioServerConnector;
import io.pivio.view.configuration.ServerConfig;
import io.pivio.view.overview.model.Connection;
import io.pivio.view.overview.model.PivioDetail;
import io.pivio.view.overview.model.Service;
import io.pivio.view.overview.model.ServiceIdShortName;
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
            List<ServiceIdShortName> serviceIdShortNames = getConnections();
            detail.setUsedBy(getUsage(detail.short_name, detail.service, serviceIdShortNames));
            return detail;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<ServiceIdShortName> getConnections() {
        String path = "/document?fields=short_name,id,service";
        String url = serverConfig.apiAddress + path;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        log.debug("Asking: " + url);
        ParameterizedTypeReference<List<ServiceIdShortName>> typeRef = new ParameterizedTypeReference<List<ServiceIdShortName>>() {
        };
        ResponseEntity<List<ServiceIdShortName>> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers), typeRef);
        log.debug(response.getBody().toString());
        return response.getBody();
    }

    List<Connection> getUsage(String serviceName, Service serviceDefinition, List<ServiceIdShortName> allServices) {
        List<Connection> result = new ArrayList<>();
        List<String> offeredServices = new ArrayList<>();

        if (serviceDefinition != null && serviceDefinition.provides != null) {
            for (Service.Provides provide : serviceDefinition.provides) {
                offeredServices.add(serviceName + "_" + provide.port);
                offeredServices.add(provide.service_name);
            }
        }

        for (ServiceIdShortName service : allServices) {
            if (service.hasDependencies()) {
                for (Service.DependsOn.Internal dependsOn : service.service.depends_on.internal) {
                    if (offeredServices.contains(dependsOn.short_name + "_" + dependsOn.port) ||
                            offeredServices.contains(dependsOn.service_name)) {
                        result.add(new Connection(service.id, dependsOn, service.short_name));
                    }
                }
            }
        }
        return result;
    }


}
