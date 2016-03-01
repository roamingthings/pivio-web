package io.pivio.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OverviewController {

    @RequestMapping(value = "/app/overview")
    public String overview(Model model) {
        return "overview";
    }

}
