package io.pivio.view.feed;

import com.fasterxml.jackson.databind.JsonNode;
import io.pivio.view.PivioController;
import io.pivio.view.configuration.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class FeedController {

    private final Logger log = LoggerFactory.getLogger(PivioController.class);

    @Autowired
    ServerConfig serverConfig;

    @RequestMapping(value = "/app/feed")
    public String overview(Model model) {

        RestTemplate restTemplate = new RestTemplate();
        List response = restTemplate.getForObject(serverConfig.apiAddress + "/changeset", List.class);
        model.addAttribute("changeset", response);
        model.addAttribute("config", serverConfig);
        model.addAttribute("pageId", "tabFeed");


        List changes = new ArrayList();

        model.addAttribute("changeset", changes);
        return "feed";
    }

    private List<FieldEntry> getFields(JsonNode change) {
        List<FieldEntry> fieldEntries = new ArrayList<>();
        JsonNode fields = change.get("fields");
        Iterator<JsonNode> fieldsIterator = fields.elements();
        while (fieldsIterator.hasNext()) {
            JsonNode field = fieldsIterator.next();
            fieldEntries.add(new FieldEntry(field.get("current").toString(),
                    field.get("previous").textValue(),
                    field.get("field").textValue()));
        }
        return fieldEntries;
    }

}
