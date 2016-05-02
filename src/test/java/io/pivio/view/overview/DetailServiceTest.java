package io.pivio.view.overview;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivio.view.overview.model.ServiceIdShortName;
import io.pivio.view.overview.model.Service;
import io.pivio.view.overview.model.Connection;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DetailServiceTest {

    ObjectMapper mapper;
    DetailService detailService;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        detailService = new DetailService();
    }

    @Test
    public void getUsageSize() throws Exception {
        Service serviceCfromFile = mapper.readValue(new File("src/test/resources/detailtest/service-c.json"), Service.class);
        List<ServiceIdShortName> allServices = mapper.readValue(new File("src/test/resources/detailtest/connections.json"), new TypeReference<List<ServiceIdShortName>>() {
        });

        List<Connection> usedby = detailService.getUsedBy("C", serviceCfromFile, allServices);

        assertThat(usedby).hasSize(2);
    }

    @Test
    public void getUsageSingle() throws Exception {
        Service serviceB = mapper.readValue(new File("src/test/resources/detailtest/service-B.json"), Service.class);
        List<ServiceIdShortName> serviceIdShortNames = mapper.readValue(new File("src/test/resources/detailtest/connections.json"), new TypeReference<List<ServiceIdShortName>>() {
        });

        List<Connection> usedby = detailService.getUsedBy("B", serviceB, serviceIdShortNames);

        assertThat(usedby.get(0).id).isEqualTo("A");
    }


}