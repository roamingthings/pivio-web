package io.pivio.view.app.about;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

@Controller
public class InfoController {

    @RequestMapping("/about")
    public String info(Model model) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL version = classLoader.getResource("VERSION");
        String gitHash = "DEV-VERSION";

        if (version != null) {
            File file = new File(version.getFile());
            if (file.exists()) {
                try {
                    gitHash = new String(Files.readAllBytes(file.toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        model.addAttribute("githash", gitHash);
        return "info";
    }
}
