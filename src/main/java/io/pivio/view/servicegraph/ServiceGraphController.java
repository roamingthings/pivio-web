package io.pivio.view.servicegraph;

import io.pivio.view.configuration.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ServiceGraphController {

  @Autowired
  ServerConfig serverConfig;

  @RequestMapping(value = "/app/servicegraph")
  public String graph(Model model) {
    model.addAttribute("config", serverConfig);
    model.addAttribute("pageId", "tabServiceGraph");
    return "servicegraph";
  }
}
