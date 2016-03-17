package io.pivio.view.overview;

import io.pivio.view.PivioServerConnector;
import io.pivio.view.configuration.ServerConfig;
import io.pivio.view.overview.model.OverviewCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OverviewService {

    @Autowired
    ServerConfig serverConfig;

    @Autowired
    PivioServerConnector pivioServerConnector;

    List<OverviewCard> getOverview() {
        List<OverviewCard> result = new ArrayList<>();
        try {
            ResponseEntity overview = pivioServerConnector.getOverviewCards();
            return (List<OverviewCard>) overview.getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
