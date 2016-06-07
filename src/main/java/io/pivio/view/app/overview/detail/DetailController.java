package io.pivio.view.app.overview.detail;

import io.pivio.view.configuration.ServerConfig;
import io.pivio.view.overview.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DetailController {

    @Autowired
    ServerConfig serverConfig;

    @Autowired
    DetailService detailService;

    @Autowired
    OverviewService overviewService;

    @RequestMapping(value = "/app/overview/{id}")
    public String detail(@PathVariable String id, Model model) {
        model.addAttribute("config", serverConfig);
        model.addAttribute("pageId", "tabOverview");
        model.addAttribute("pivio", detailService.getDetail(id));
        return "detail";
    }
}
