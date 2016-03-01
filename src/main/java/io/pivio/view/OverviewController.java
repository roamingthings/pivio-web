package io.pivio.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OverviewController {

    private final Logger log = LoggerFactory.getLogger(PivioController.class);

    @Autowired
    ServerConfig serverConfig;


    @RequestMapping("/app/overview")
    public String overview(Model model) {
        model.addAttribute("config", serverConfig);
        return "overview";
    }

}
