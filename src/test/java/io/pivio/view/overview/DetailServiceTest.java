package io.pivio.view.overview;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivio.view.app.overview.detail.ServiceIdShortName;
import io.pivio.view.overview.model.Service;
import io.pivio.view.app.overview.detail.view.DependentServiceViewModel;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DetailServiceTest {

    ObjectMapper mapper;
    DetailService2 detailService;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        detailService = new DetailService2();
    }

    @Test
    public void getUsageSize() throws Exception {
        Service serviceCfromFile = mapper.readValue(new File("src/test/resources/detailtest/service-c.json"), Service.class);
        List<ServiceIdShortName> allServices = mapper.readValue(new File("src/test/resources/detailtest/connections.json"), new TypeReference<List<ServiceIdShortName>>() {
        });

        List<DependentServiceViewModel> usedby = detailService.getUsedBy("C", serviceCfromFile, allServices);

        assertThat(usedby).hasSize(2);
    }

    @Test
    public void getUsageSingle() throws Exception {
        Service serviceB = mapper.readValue(new File("src/test/resources/detailtest/service-B.json"), Service.class);
        List<ServiceIdShortName> serviceIdShortNames = mapper.readValue(new File("src/test/resources/detailtest/connections.json"), new TypeReference<List<ServiceIdShortName>>() {
        });

        List<DependentServiceViewModel> usedby = detailService.getUsedBy("B", serviceB, serviceIdShortNames);

        assertThat(usedby.get(0).id).isEqualTo("A");
    }


}