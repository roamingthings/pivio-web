package io.pivio.view.app.overview.list;

import io.pivio.view.configuration.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ListController {

    @Autowired
    ServerConfig serverConfig;

    @Autowired
    ListService listService;

    @RequestMapping("/")
    public String main() {
        return "redirect:/app/overview";
    }

    @RequestMapping("/app/overview")
    public String overview(Model model) {
        model.addAttribute("config", serverConfig);
        model.addAttribute("pageId", "tabOverview");
        List<OverviewModel> overview = new ArrayList<>();
        try {
            overview = listService.getShortInfo();
        } catch (IOException e) {
            model.addAttribute("pivioErrorMessage", "Document server is not reachable. Please try again later.");
        }
        model.addAttribute("pivioOverview", overview);
        return "overview";
    }
}
