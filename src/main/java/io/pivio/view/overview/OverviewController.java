package io.pivio.view.overview;

import io.pivio.view.configuration.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OverviewController {

    private final Logger log = LoggerFactory.getLogger(OverviewController.class);

    @Autowired
    ServerConfig serverConfig;

    @Autowired
    DetailService detailService;

    @RequestMapping("/")
    public String main() {
        return "redirect:/app/overview";
    }

    @RequestMapping("/app/overview")
    public String overview(Model model) {
        model.addAttribute("config", serverConfig);
        model.addAttribute("pageId", "tabOverview");
        return "overview";
    }

    @RequestMapping("/app/overview/detail/{id}")
    public String detail(@PathVariable String id, Model model) {
        model.addAttribute("config", serverConfig);
        model.addAttribute("pageId", "tabOverview");
        model.addAttribute("pivioDocumentId", id);
        return "detail";
    }

    @RequestMapping("/app/overview/details/{id}")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("config", serverConfig);
        model.addAttribute("pageId", "tabOverview");
        model.addAttribute("pivio", detailService.getDetail(id));
        return "details";
    }


    @RequestMapping("/app/overview/list")
    public String similar(@RequestParam("field") String field, @RequestParam("value") String value, Model model) {
        model.addAttribute("config", serverConfig);
        model.addAttribute("pageId", "tabOverview");
        model.addAttribute("field", field);
        model.addAttribute("value", value);
        return "list";
    }

}
