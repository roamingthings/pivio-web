package io.pivio.view;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivio.view.overview.PivioDetail;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class PivioServerConnectorTest {

    @Test
    public void mappingTest() throws IOException {
        String json = "{\"vcsroot\":\"git@github.com:pivio/pivio-client.git\",\"software_dependencies\":[],\"documentversion\":\"1\",\"contact\":\"Oliver Wehrens\",\"name\":\"Pivio Client\",\"description\":\"Reads descriptions out of source repositories and extracts information to send it to the pivio server.\",\"links\":{\"Homepage\":\"http://none\",\"Buildchain\":\"http://ci.local\"},\"team\":\"Pivio\",\"id\":\"349534957349857387534\",\"type\":\"Tool\",\"created\":\"2016-03-11T13:19:27.505+01:00\",\"lastUpload\":\"2016-03-14T14:14:58.190+01:00\",\"lastUpdate\":\"2016-03-11T13:19:27.505+01:00\"}";
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PivioDetail pivioDetail = objectMapper.readValue(json, PivioDetail.class);
        assertThat(pivioDetail.id).isEqualTo("349534957349857387534");
    }
}