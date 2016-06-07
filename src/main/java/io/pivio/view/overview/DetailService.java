package io.pivio.view.overview;

import io.pivio.view.PivioServerConnector;
import io.pivio.view.configuration.ServerConfig;
import io.pivio.view.app.overview.detail.view.DependentServiceViewModel;
import io.pivio.view.overview.model.PivioDetail;
import io.pivio.view.overview.model.Service;
import io.pivio.view.app.overview.detail.ServiceIdShortName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
class DetailService2 {

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
            detail.setUsedBy(getUsedBy(detail.short_name, detail.service, serviceIdShortNames));
            detail.setInternalUsage(serviceIdShortNames);
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

    List<DependentServiceViewModel> getUsedBy(String serviceName, Service serviceDefinition, List<ServiceIdShortName> allServices) {
        List<DependentServiceViewModel> result = new ArrayList<>();
        List<String> offeredServices = new ArrayList<>();

        if (serviceDefinition != null && serviceDefinition.provides != null) {
            for (Service.Provides provide : serviceDefinition.provides) {
                offeredServices.add(serviceName + "_" + provide.port);
                offeredServices.add(provide.service_name);
            }
        }

//        for (ServiceIdShortName otherService : allServices) {
//            if (otherService.hasDependencies()) {
//                for (Service.DependsOn.Internal otherServiceDependsOn : otherService.service.depends_on.internal) {
//                    String serviceString = otherServiceDependsOn.short_name + "_" + otherServiceDependsOn.port;
//                    if (offeredServices.contains(serviceString) || offeredServices.contains(otherServiceDependsOn.service_name)) {
//                        result.add(new Connection(otherService.id, otherServiceDependsOn, otherService.short_name));
//                    }
//                }
//            }
//        }
        Collections.sort(result);
        return result;
    }


}
