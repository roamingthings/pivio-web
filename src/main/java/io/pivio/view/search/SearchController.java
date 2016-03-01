package io.pivio.view.search;

import io.pivio.view.configuration.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {

    @Autowired
    ServerConfig serverConfig;

    @RequestMapping(value = "/app/search")
    public String search(Model model) {
        model.addAttribute("config", serverConfig);
        model.addAttribute("pageId", "tabSearch");
        return "search";
    }
}
