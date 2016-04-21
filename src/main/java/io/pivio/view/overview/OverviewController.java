package io.pivio.view.overview;

import io.pivio.view.configuration.ServerConfig;
import io.pivio.view.overview.model.OverviewCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OverviewController {

    private final Logger log = LoggerFactory.getLogger(OverviewController.class);

    @Autowired
    ServerConfig serverConfig;

    @Autowired
    DetailService detailService;

    @Autowired
    OverviewService overviewService;

    @RequestMapping("/")
    public String main() {
        return "redirect:/app/overview";
    }

    @RequestMapping("/app/overview")
    public String overview(Model model ) {
        model.addAttribute("config", serverConfig);
        model.addAttribute("pageId", "tabOverview");
        List<OverviewCard> overview = new ArrayList<>();
        try {
            overview = overviewService.getOverview();
        } catch (IOException e) {
            model.addAttribute("pivioErrorMessage", "Document server is not reachable. Please try again later.");
        }
        model.addAttribute("pivioOverview", overview);
        return "overview";
    }

    @RequestMapping(value = "/app/overview/{id}")
    public String detail(@PathVariable String id, Model model) {
        model.addAttribute("config", serverConfig);
        model.addAttribute("pageId", "tabOverview");
        model.addAttribute("pivio", detailService.getDetail(id));
        return "detail";
    }

    @RequestMapping(value = "/app/overview/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            if (overviewService.deleteDocument(id)) {
                redirectAttributes.addFlashAttribute("pivioMessage", "Document with id " + id + " was deleted.");
            } else {
                redirectAttributes.addFlashAttribute("pivioErrorMessage", "Document with id " + id + " was NOT deleted.");
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("pivioErrorMessage", "Document with id " + id + " was NOT deleted. Could not communicate with document server.");
        }
        return "redirect:/app/overview";
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
