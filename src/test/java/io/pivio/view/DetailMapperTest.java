package io.pivio.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivio.view.overview.model.PivioDetail;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class DetailMapperTest {

    @Test
    public void testMapDetail() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        PivioDetail pivioDetail = mapper.readValue(new File("src/test/resources/detail.json"), PivioDetail.class);

        assertThat(pivioDetail.id).isEqualTo("next-generation-print-2342-2413-9189-1990");
    }

    @Test
    public void testMapDetailLicenses() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        PivioDetail pivioDetail = mapper.readValue(new File("src/test/resources/DetailWithLicenses.json"), PivioDetail.class);

        assertThat(pivioDetail.id).isEqualTo("349534957349857387534");
    }


}
